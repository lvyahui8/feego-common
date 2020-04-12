package io.github.lvyahui8.configuration.processor;

import com.sun.source.tree.CompilationUnitTree;
import com.sun.source.tree.Tree;
import com.sun.source.util.TreePath;
import com.sun.source.util.Trees;
import com.sun.tools.javac.model.JavacElements;
import com.sun.tools.javac.processing.JavacProcessingEnvironment;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.TreeMaker;
import com.sun.tools.javac.util.Context;
import com.sun.tools.javac.util.List;
import com.sun.tools.javac.util.Names;
import io.github.lvyahui8.configuration.annotations.ModuleLoggerAutoGeneration;
import io.github.lvyahui8.configuration.annotations.RuntimeConfiguration;
import io.github.lvyahui8.configuration.utils.BuildUtils;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;
import java.io.IOException;
import java.io.Writer;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @author yahui.lv lvyahui8@gmail.com
 * @date 2020/4/12 21:15
 */
public class RuntimeConfigurationProcessor extends AbstractProcessor {

    private Messager messager;
    private Filer filer;
    private JavacElements elementUtils;
    private Trees trees;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        messager = processingEnv.getMessager();
        filer = processingEnv.getFiler();
        this.elementUtils = (JavacElements) processingEnv.getElementUtils();
        this.trees = Trees.instance(processingEnv);
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(RuntimeConfiguration.class);
        if (! elements.isEmpty()) {
            for (Element element : elements) {
                String packageName = BuildUtils.getPackageName(element);
                JavaFileObject sourceFile;
                Writer writer;
                String sourceClassName = element.getSimpleName().toString();
                String outputClassName = sourceClassName + "Instance";
                try {
                    sourceFile = filer.createSourceFile(packageName + "." + outputClassName);
                    writer = sourceFile.openWriter();
                } catch (Exception e) {
                    messager.printMessage(Diagnostic.Kind.ERROR,"create java file failed. eMsg:" + e.getMessage());
                    return false;
                }
                TreePath treesPath = trees.getPath(element);
                Tree leaf = treesPath.getLeaf();
                List<JCTree.JCImport> imports = null;
                if (treesPath.getCompilationUnit() instanceof JCTree.JCCompilationUnit && leaf instanceof JCTree) {
                    JCTree.JCCompilationUnit compilationUnit = (JCTree.JCCompilationUnit) treesPath.getCompilationUnit();
                    imports = compilationUnit.getImports();
                }

                JCTree.JCClassDecl classDecl = (JCTree.JCClassDecl) elementUtils.getTree(element);
                messager.printMessage(Diagnostic.Kind.NOTE,"output class name:" + outputClassName);
                try {
                    writer.write("package " + packageName + ";\n\n");
                    if (imports != null) {
                        for (JCTree importTree : imports) {
                            JCTree.JCImport jcImport = (JCTree.JCImport) importTree;
                            String item = jcImport.getQualifiedIdentifier().toString();
                            writer.write("import " + item + ";\n");
                        }
                    }
                    writer.write("\npublic class " + outputClassName + " {\n");
                    writer.write("  private static " + sourceClassName + " instance = new " + sourceClassName + "();\n");
                    if (classDecl != null && classDecl.defs != null) {
                        for (JCTree item : classDecl.defs) {
                            if (Tree.Kind.METHOD.equals(item.getKind())) {
                                JCTree.JCMethodDecl methodDecl = (JCTree.JCMethodDecl) item;
                                String itemName = methodDecl.getName().toString();
                                if ("<init>".equals(itemName) || methodDecl.getReturnType() == null) {
                                    // 构造方法跳过
                                    continue;
                                }
                                String returnType = methodDecl.getReturnType().toString();
                                writer.write("  public static " + returnType + ' ' + itemName + "() {\n");
                                writer.write("      return instance." + itemName + "();\n");
                                writer.write("  }\n");
                            } else if (Tree.Kind.VARIABLE.equals(item.getKind())){
                                JCTree.JCVariableDecl variableDecl = (JCTree.JCVariableDecl) item;
                                String itemName = variableDecl.getName().toString();
                                String returnType = variableDecl.getType().toString();
                                String methodName = ("boolean".equals(returnType) ? "is" : "get") + StringUtils.capitalize(itemName);
                                writer.write("  public static " + returnType + ' ' + methodName + "() {\n");
                                writer.write("      return instance." + methodName + "();\n");
                                writer.write("  }\n");
                            }
                        }
                    }
                    writer.write("}\n");
                    writer.flush();
                } catch (IOException e) {
                    messager.printMessage(Diagnostic.Kind.ERROR,"writer java source file failed. eMsg:" + e.getMessage());
                    return false;
                } catch (Exception e) {
                    messager.printMessage(Diagnostic.Kind.ERROR,"unknown exception, eMsg:" + e.getMessage());
                    return false;
                }
                finally {
                    try {
                        writer.close();
                    } catch (IOException ignored) {

                    }
                }
            }
        }
        return false;
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return new HashSet<>(Collections.singletonList(RuntimeConfiguration.class.getCanonicalName()));
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }
}
