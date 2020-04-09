package io.github.lvyahui8.configuration.processor;

import com.sun.tools.javac.code.Flags;
import com.sun.tools.javac.model.JavacElements;
import com.sun.tools.javac.processing.JavacProcessingEnvironment;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.TreeMaker;
import com.sun.tools.javac.util.Context;
import com.sun.tools.javac.util.List;
import com.sun.tools.javac.util.ListBuffer;
import com.sun.tools.javac.util.Names;
import io.github.lvyahui8.configuration.annotations.ModuleLoggerAutoGeneration;
import io.github.lvyahui8.configuration.annotations.Singleton;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Name;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * 参考：
 * - https://blog.csdn.net/a_zhenzhen/article/details/86065063#TreeMaker.Block
 * - https://zhuanlan.zhihu.com/p/121031315
 * - https://www.cnblogs.com/kanyun/p/11541826.html
 * - http://www.javaet.com/blog/136.htm
 * - https://blog.csdn.net/A_zhenzhen/article/details/86065063
 *
 * 编译器修改的语法树，Intellij idea识别不出来，会飘红
 * https://stackoverflow.com/questions/15357362/can-i-modify-the-ast-before-the-java-compiler-compiling-the-ast-to-class-file
 * https://stackoverflow.com/questions/48605327/intellij-cannot-recognize-classes-modified-by-annotation-processor-in-target-cla
 * https://www.jianshu.com/p/554c5491bea6
 *
 * @author yahui.lv lvyahui8@gmail.com
 * @date 2020/4/8 21:05
 */
public class SingletonProcessor extends AbstractProcessor {

    private Messager messager;
    private JavacElements elementUtils;
    private TreeMaker treeMaker;
    private Names names;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        messager = processingEnv.getMessager();
        this.elementUtils = (JavacElements) processingEnv.getElementUtils();
        Context context = ((JavacProcessingEnvironment) processingEnv).getContext();
        this.treeMaker = TreeMaker.instance(context);
        this.names = Names.instance(context);
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(Singleton.class);
        messager.printMessage(Diagnostic.Kind.NOTE,"process singleton， size:" + elements.size());
        if (! elements.isEmpty()) {
            for (Element element : elements) {
                try {
                    JCTree.JCClassDecl classDecl = (JCTree.JCClassDecl) elementUtils.getTree(element);
                    String className = element.getSimpleName().toString();
                    String staticFieldName = "instance";
                    messager.printMessage(Diagnostic.Kind.NOTE,"singleton name:" + className);
                    JCTree.JCNewClass jcNewClass = treeMaker.NewClass(null,
                            /* 暂时不支持类型参数 */
                            List.nil(),
                            treeMaker.Ident(names.fromString(className)),
                            /* 暂时部支持构造函数入参 */
                            List.nil(),
                            null
                    );
                    classDecl.defs = classDecl.defs.append(treeMaker.VarDef(treeMaker.Modifiers(Flags.STATIC + Flags.PRIVATE),
                            names.fromString(staticFieldName),treeMaker.Ident(names.fromString(className)),jcNewClass));
                    ListBuffer<JCTree.JCStatement> methodBlockStatements = new ListBuffer<>();
                    JCTree.JCReturn jcReturn = treeMaker.Return(treeMaker.Ident(names.fromString(staticFieldName)));
                    methodBlockStatements.add(jcReturn);
                    JCTree.JCBlock methodBlock = treeMaker.Block(0,methodBlockStatements.toList());
                    classDecl.defs = classDecl.defs.append(treeMaker.MethodDef(treeMaker.Modifiers(Flags.PUBLIC + Flags.STATIC),
                            names.fromString("getInstance"),
                            treeMaker.Ident(names.fromString(className)),
                            List.nil(),
                            List.nil(),
                            List.nil(),
                            methodBlock,
                            null
                            ));
                } catch (Exception e) {
                    messager.printMessage(Diagnostic.Kind.ERROR,"singleton handle error: " + e.getMessage(),element);
                }

            }
        }
        return false;
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return new HashSet<>(Collections.singletonList(Singleton.class.getCanonicalName()));
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }
}
