package io.github.lvyahui8.example.reddot;

import io.github.lvyahui8.sdk.reddot.RedDot;

import java.util.LinkedList;
import java.util.List;

/**
 * @author feego lvyahui8@gmail.com
 * @date 2021/1/22
 */
@SuppressWarnings({"unused"})
public enum AppRedDot implements RedDot {
    root(null),
    hp(root),
    channels(hp),
    my_account(root),
    profile(my_account),
    asset(profile),
    ;

    AppRedDot parent;

    List<AppRedDot> children = new LinkedList<>();

    AppRedDot(AppRedDot parent) {
        this.parent = parent;
        if (parent != null) {
            parent.children.add(this);
        }
    }

    @Override
    public String id() {
        return  name();
    }

    @Override
    public RedDot parent() {
        return parent;
    }

    @Override
    public boolean isLeaf() {
        return children.isEmpty();
    }
}
