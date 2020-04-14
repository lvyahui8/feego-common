package io.github.lvyahui8.configuration.processor;

import io.github.lvyahui8.configuration.annotations.ResourceStrings;
import io.github.lvyahui8.configuration.annotations.Singleton;
import io.github.lvyahui8.configuration.utils.BuildUtils;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.net.URL;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

/**
 * @author yahui.lv lvyahui8@gmail.com
 * @date 2020/4/14 22:31
 */
public class ResourceStringsProcessor extends AbstractProcessor {

    private Messager messager;
    private Filer filer;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        messager = processingEnv.getMessager();
        filer = processingEnv.getFiler();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(ResourceStrings.class);
        if (! elements.isEmpty()) {
            for (Element element : elements) {
                String prefix = BuildUtils.getPackageName(element);
                String classSimpleName = "MultipleLienStrings";
                String className = prefix + '.' + classSimpleName;
                JavaFileObject sourceFile;
                Writer writer;
                try {
                    sourceFile = filer.createSourceFile(className);
                    writer = sourceFile.openWriter();
                } catch (Exception e) {
                    messager.printMessage(Diagnostic.Kind.ERROR,"create java file failed. eMsg:" + e.getMessage());
                    continue;
                }
                try {
                    writer.write("package " + prefix + ";\n\n");
                    writer.write("import java.io.*;\n");
                    writer.write("import org.apache.commons.io.IOUtils;\n");
                    writer.write("import java.nio.charset.StandardCharsets;\n");
                    writer.write("\npublic enum " + classSimpleName + " { \n ");
                    ResourceStrings resourceStrings = element.getAnnotation(ResourceStrings.class);
                    URL resources = getClass().getClassLoader().getResource(resourceStrings.value());
                    if (resources == null) {
                        messager.printMessage(Diagnostic.Kind.WARNING,"resources strings not found. " + resourceStrings.value());
                        continue;
                    }
                    File dir = new File(resources.getFile());
                    if (! dir.isDirectory()) {
                        messager.printMessage(Diagnostic.Kind.WARNING,"resources strings must be directory");
                        continue;
                    }
                    handleResourceStrings(dir,dir,writer);
                    writer.write("\n  ;\n");
                    writer.write("  private static final String rootPath = \"" + resourceStrings.value() + "\";\n");
                    writer.write("  private String file;\n");
                    writer.write("  " + classSimpleName + "(String file) {\n");
                    writer.write("      this.file = file;\n");
                    writer.write("  }\n");
                    writer.write("  public String getContent() {\n");
                    writer.write("      try{return IOUtils.toString(getClass().getClassLoader().getResourceAsStream(rootPath + file),StandardCharsets.UTF_8.name());}catch(Exception e){return null;}\n");
                    writer.write("  }\n");
                    writer.write("  @Override public String toString() {\n");
                    writer.write("      return getContent();\n");
                    writer.write("  }\n");
                    writer.write("}\n");
                } catch (Exception e) {
                    messager.printMessage(Diagnostic.Kind.ERROR,"write source code failed. eMsg:" + e.getMessage());
                } finally {
                    try {
                        writer.close();
                    } catch (IOException ignored) {

                    }
                }
            }

        }
        return false;
    }

    void handleResourceStrings(File root,File dir, Writer writer) throws IOException {
        if (! dir.isDirectory()) {
            return;
        }
        File[] files = dir.listFiles();
        if (files != null && files.length > 0) {
            for (File file : files) {
                if (file.isDirectory()) {
                    handleResourceStrings(root,file,writer);
                } else {
                    String absolutePath = file.getAbsolutePath();
                    String rootPath = root.getAbsolutePath();
                    String diff = absolutePath.replace(rootPath,"");
                    String staticFieldName = StringUtils.strip(diff,"\\").replaceAll("[\\\\.]","_");
                    writer.write("  " + staticFieldName + "(\""+ diff.replaceAll("\\\\", "/") +"\"),\n");
                }
            }
        }
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return new HashSet<>(Collections.singletonList(ResourceStrings.class.getCanonicalName()));
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }
}
