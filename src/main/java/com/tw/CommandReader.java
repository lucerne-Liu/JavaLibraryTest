package com.tw;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CommandReader {
    private Scanner scanner = new Scanner(System.in);

    public String read(int flag) throws Exception {
        scanner.useDelimiter("\n");
        String inputString = scanner.next();
        return validate(inputString, flag) ? inputString : "invalid";
    }

    public boolean validate(String inputString, int flag) {
        //格式：姓名, 学号, 学科: 成绩, 学科: 成绩, 学科: 成绩, 学科: 成绩（至少有一门成绩）
        String[] arr = inputString.split(",");
        List<String> list = Stream.of(arr).map(item -> item.trim()).collect(Collectors.toList());
        Pattern pattern = Pattern.compile("-?[0-9]*");
        //validate the input studentInfo
        if (flag == 0) {
            //id is not digit
            if (!pattern.matcher(list.get(1)).matches() || arr.length < 3) {
                return false;
            }
            for (int i = 0; i < list.size(); i++) {
                if (i > 1) {
                    //judge whether it contains ": " and the score is digit
                    if (!list.get(i).contains(":") || !pattern.matcher(list.get(i).split(":")[1].trim()).matches()) {
                        return false;
                    }
                }
            }
        } else if (flag == 1) {
            //validate the student id list
            return list.stream().allMatch(item -> pattern.matcher(item).matches());
        } else {
            return pattern.matcher(inputString).matches();
        }

        return true;
    }
}
