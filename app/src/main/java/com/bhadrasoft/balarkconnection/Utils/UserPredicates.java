package com.bhadrasoft.balarkconnection.Utils;

import android.os.Build;
import android.support.annotation.RequiresApi;

import com.bhadrasoft.balarkconnection.models.User;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Created by riddhi on 24-Dec-17.
 */

public class UserPredicates {

    public static Predicate<User> isContainsText(final String searchText) {
        return p -> p.getFirstName().toLowerCase().contains(searchText.toLowerCase())
                || p.getLastName().toLowerCase().contains(searchText.toLowerCase())
                || p.getMiddleName().toLowerCase().contains(searchText.toLowerCase());
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static List<User> filterUsers(List<User> users, Predicate<User> predicate) {
        return users.stream().filter(predicate).collect(Collectors.toList());
    }

}
