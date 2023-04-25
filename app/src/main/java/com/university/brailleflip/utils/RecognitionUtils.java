package com.university.brailleflip.utils;

import android.util.ArraySet;

//for input braille

public class RecognitionUtils {


    private static boolean isCapitalSign;
    private static boolean isNumber;


    public static String recognition(ArraySet<Integer> arraySet){
        int size = arraySet.size();
        if (size==1){//a capital Comma
            if(arraySet.contains(1)){
                if (isNumber){
                    isNumber=false;
                    return "1";
                }
                if (isCapitalSign){
                    isCapitalSign=false;
                    return "A";
                }
                return "a";
            }
            if(arraySet.contains(2)){
                return ",";
            }
            if(arraySet.contains(6)){
                isCapitalSign=true;
                return "Capital";
            }

        }

        if (size==2) {//b c e i k w Semicolon
            if (arraySet.contains(1)&&arraySet.contains(2)){
                if (isNumber){
                    isNumber=false;
                    return "2";
                }
                if (isCapitalSign){
                    isCapitalSign=false;
                    return "B";
                }
                return "b";
            }
            if (arraySet.contains(1)&&arraySet.contains(4)){
                if (isNumber){
                    isNumber=false;
                    return "3";
                }
                if (isCapitalSign){
                    isCapitalSign=false;
                    return "C";
                }
                return "c";
            }
            if (arraySet.contains(1)&&arraySet.contains(5)){
                if (isNumber){
                    isNumber=false;
                    return "5";
                }
                if (isCapitalSign){
                    isCapitalSign=false;
                    return "E";
                }
                return "e";
            }
            if (arraySet.contains(2)&&arraySet.contains(4)){
                if (isNumber){
                    isNumber=false;
                    return "9";
                }
                if (isCapitalSign){
                    isCapitalSign=false;
                    return "I";
                }
                return "i";
            }
            if (arraySet.contains(1)&&arraySet.contains(3)){
                if (isCapitalSign){
                    isCapitalSign=false;
                    return "K";
                }
                return "k";
            }
            if (arraySet.contains(2)&&arraySet.contains(3)){
                return ";";
            }
        }

        if (size==3) {//d f h j l m o s u ? ! . " "
            if (arraySet.contains(1)&&arraySet.contains(4)&&arraySet.contains(5)){
                if (isNumber){
                    isNumber=false;
                    return "4";
                }
                if (isCapitalSign){
                    isCapitalSign=false;
                    return "D";
                }
                return "d";
            }
            if (arraySet.contains(1)&&arraySet.contains(4)&&arraySet.contains(2)){
                if (isNumber){
                    isNumber=false;
                    return "6";
                }
                if (isCapitalSign){
                    isCapitalSign=false;
                    return "F";
                }
                return "f";
            }
            if (arraySet.contains(1)&&arraySet.contains(2)&&arraySet.contains(5)){
                if (isNumber){
                    isNumber=false;
                    return "8";
                }
                if (isCapitalSign){
                    isCapitalSign=false;
                    return "H";
                }
                return "h";
            }
            if (arraySet.contains(2)&&arraySet.contains(4)&&arraySet.contains(5)){
                if (isCapitalSign){
                    isCapitalSign=false;
                    return "J";
                }
                return "j";
            }
            if (arraySet.contains(1)&&arraySet.contains(2)&&arraySet.contains(3)){
                if (isCapitalSign){
                    isCapitalSign=false;
                    return "L";
                }
                return "l";
            }
            if (arraySet.contains(1)&&arraySet.contains(4)&&arraySet.contains(3)){
                if (isCapitalSign){
                    isCapitalSign=false;
                    return "M";
                }
                return "m";
            }
            if (arraySet.contains(1)&&arraySet.contains(5)&&arraySet.contains(3)){
                if (isCapitalSign){
                    isCapitalSign=false;
                    return "O";
                }
                return "o";
            }
            if (arraySet.contains(2)&&arraySet.contains(3)&&arraySet.contains(4)){
                if (isCapitalSign){
                    isCapitalSign=false;
                    return "S";
                }
                return "s";
            }
            if (arraySet.contains(1)&&arraySet.contains(3)&&arraySet.contains(6)){
                if (isCapitalSign){
                    isCapitalSign=false;
                    return "U";
                }
                return "u";
            }

            if (arraySet.contains(2)&&arraySet.contains(3)&&arraySet.contains(6)){
                return "?";
            }
            if (arraySet.contains(2)&&arraySet.contains(5)&&arraySet.contains(3)){
                return "!";
            }
            if (arraySet.contains(2)&&arraySet.contains(5)&&arraySet.contains(6)){
                return ".";
            }
//            if (arraySet.contains(2)&&arraySet.contains(3)&&arraySet.contains(6)){
//                return ".";
//            }
//            if (arraySet.contains(2)&&arraySet.contains(5)&&arraySet.contains(6)){
//                return ".";
//            }
        }


        if (size==4) {//g n p r t v w x z
            if (arraySet.contains(3) && arraySet.contains(4) &&
                    arraySet.contains(5)&& arraySet.contains(6)) {
                isNumber=true;
                return "Number";
            }

            if (arraySet.contains(1) && arraySet.contains(2) &&
                    arraySet.contains(4)&& arraySet.contains(5)) {
                if (isNumber){
                    isNumber=false;
                    return "7";
                }
                if (isCapitalSign){
                    isCapitalSign=false;
                    return "G";
                }
                return "g";
            }
            if (arraySet.contains(1) && arraySet.contains(3) &&
                    arraySet.contains(4)&& arraySet.contains(5)) {
                if (isCapitalSign){
                    isCapitalSign=false;
                    return "N";
                }
                return "n";
            }
            if (arraySet.contains(1) && arraySet.contains(2) &&
                    arraySet.contains(3)&& arraySet.contains(4)) {
                if (isCapitalSign){
                    isCapitalSign=false;
                    return "P";
                }
                return "p";
            }
            if (arraySet.contains(1) && arraySet.contains(2) &&
                    arraySet.contains(3)&& arraySet.contains(5)) {
                if (isCapitalSign){
                    isCapitalSign=false;
                    return "R";
                }
                return "r";
            }
            if (arraySet.contains(2) && arraySet.contains(3) &&
                    arraySet.contains(4)&& arraySet.contains(5)) {
                if (isCapitalSign){
                    isCapitalSign=false;
                    return "T";
                }
                return "t";
            }
            if (arraySet.contains(1) && arraySet.contains(2) &&
                    arraySet.contains(3)&& arraySet.contains(6)) {
                if (isCapitalSign){
                    isCapitalSign=false;
                    return "V";
                }
                return "v";
            }
            if (arraySet.contains(2) && arraySet.contains(4) &&
                    arraySet.contains(5)&& arraySet.contains(6)) {
                if (isCapitalSign){
                    isCapitalSign=false;
                    return "W";
                }
                return "w";
            }
            if (arraySet.contains(1) && arraySet.contains(3) &&
                    arraySet.contains(4)&& arraySet.contains(6)) {
                if (isCapitalSign){
                    isCapitalSign=false;
                    return "X";
                }
                return "x";
            }
            if (arraySet.contains(1) && arraySet.contains(3) &&
                    arraySet.contains(5)&& arraySet.contains(6)) {
                if (isCapitalSign){
                    isCapitalSign=false;
                    return "Z";
                }
                return "z";
            }

        }


        if (size==5) {//q y
            if (arraySet.contains(1) && arraySet.contains(2) &&
                    arraySet.contains(3) && arraySet.contains(4)&& arraySet.contains(5)) {
                if (isCapitalSign){
                    isCapitalSign=false;
                    return "Q";
                }
                return "q";
            }
            if (arraySet.contains(1) && arraySet.contains(3) &&
                    arraySet.contains(4) && arraySet.contains(5)&& arraySet.contains(6)) {
                if (isCapitalSign){
                    isCapitalSign=false;
                    return "Y";
                }
                return "y";
            }
        }


        return "";
    }
}
