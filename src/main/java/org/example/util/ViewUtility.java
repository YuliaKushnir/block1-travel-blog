package org.example.util;

import org.example.view.View;

import java.util.Scanner;

public class ViewUtility {
    private static Scanner sc = new Scanner(System.in);

    public static int chooseItemFromMenu(View view){
        view.showMessage(view.MENU);
        int item;

        while(!sc.hasNextInt() || (item = sc.nextInt()) > 2 || item < 0) {
            view.showMessage(view.WRONG_INPUT);
            view.showMessage(view.MENU);
        }

        return item;
    }
}
