package io.github.lvyahui8.configuration.utils;

import org.apache.commons.lang3.StringUtils;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.PackageElement;
import javax.tools.Diagnostic;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * @author yahui.lv lvyahui8@gmail.com
 * @date 2020/4/12 21:24
 */
public class BuildUtils {
    public static String getPackageName(Element element) {
        Element enclosing = element;
        while (enclosing.getKind() != ElementKind.PACKAGE) {
            enclosing = enclosing.getEnclosingElement();
        }
        PackageElement packageElement = (PackageElement) enclosing;
        return packageElement.getQualifiedName().toString();
    }

    public static String getCommonPackagePrefix(Set<? extends Element> elements) {
        List<String> classes = new LinkedList<>();
        for (Element element : elements) {
            classes.add(getPackageName(element));
        }

        String [] items  = new String[classes.size()];
        classes.toArray(items);
        return StringUtils.getCommonPrefix(items);
    }
}
