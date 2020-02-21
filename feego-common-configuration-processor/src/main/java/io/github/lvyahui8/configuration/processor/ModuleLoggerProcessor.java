package io.github.lvyahui8.configuration.processor;

import io.github.lvyahui8.configuration.annotations.ModuleLoggerAutoGeneration;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;
import java.io.IOException;
import java.io.Writer;
import java.util.*;

/**
 * @author lvyahui (lvyahui8@gmail.com,lvyahui8@126.com)
 * @since 2020/2/21 20:08
 */
public class ModuleLoggerProcessor extends AbstractProcessor {

    public static final String LOGGER_ENUM_CLASS = "io.github.lvyahui8.core.logging.SystemLogger";

    private Messager messager;
    private Filer    filer;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        messager = processingEnv.getMessager();
        filer = processingEnv.getFiler();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(
                ModuleLoggerAutoGeneration.class);
        messager.printMessage(Diagnostic.Kind.NOTE,"process module loggers");
        if (! elements.isEmpty()) {

            for (Element element : elements) {
                ModuleLoggerAutoGeneration moduleLoggerAutoGeneration = element.getAnnotation(ModuleLoggerAutoGeneration.class);
                List<String> moduleNames = Arrays.asList(moduleLoggerAutoGeneration.value());

                JavaFileObject sourceFile ;
                Writer writer ;
                try {
                    sourceFile = filer.createSourceFile(LOGGER_ENUM_CLASS);
                    writer = sourceFile.openWriter();
                } catch (Exception e) {
                    messager.printMessage(Diagnostic.Kind.ERROR,"create java file failed. eMsg:" + e.getMessage());
                    return false;
                }

                try {
                    writer.write("package " + LOGGER_ENUM_CLASS.substring(0,LOGGER_ENUM_CLASS.lastIndexOf('.')) + ";\n\n");
                    writer.write("import io.github.lvyahui8.core.logging.ModuleLogger;\n");
                    writer.write("import org.slf4j.Logger;\n");
                    writer.write("import java.util.*;\n\n");
                    writer.write("public enum SystemLogger implements ModuleLogger { \n");
                    for (String module : moduleNames) {
                        writer.write("  " + module + ",\n");
                    }
                    writer.write("  ;\n\n");
                    writer.write("  @Override\n");
                    writer.write("  public Logger getInnerLogger() {\n");
                    writer.write("    return ModuleLoggerRepository.getModuleLogger(this.name());\n");
                    writer.write("  }\n");
                    writer.write("}\n");
                    writer.flush();
                } catch (Exception e) {
                    messager.printMessage(Diagnostic.Kind.ERROR,"handle element failed. eMsg:" + e.getMessage());
                    return false;
                } finally {
                    try {
                        writer.close();
                    } catch (IOException ignored) {
                        //
                    }
                }
            }


        }
        /*  返回false表示这个源文件需要后续的Processor继续处理 ， 返回为true表示不需要 */
        return false;
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return new HashSet<>(Collections.singletonList(ModuleLoggerAutoGeneration.class.getCanonicalName()));
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }
}
