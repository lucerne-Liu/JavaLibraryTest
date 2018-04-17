package com.tw;

import java.util.*;

public class Library {
    public static final String STUDENT_INFO_FORMAT = "（格式：姓名, 学号, 学科: 成绩, ...）";
    public static final String INPUT_ID_FORMAT = "输入要打印的学生的学号（格式： 学号, 学号,...），按回车提交：";
    public static final String DIVIDER = "========================\n";
    private CommandReader reader;
    private List<Student> students;

    public Library() {
    }

    public Library(CommandReader reader, List<Student> students) {
        this.reader = reader;
        this.students = new ArrayList<>(students);
    }

    public boolean printMainMenu() throws Exception {
        //为了测试输出把System.out.println()改为System.out.print()
        System.out.print("1. 添加学生\n2. 生成成绩单\n3. 退出\n请输入你的选择（1～3）：\n");
        switch (Integer.parseInt(reader.read(2))) {
            case 1:
                addStudent();
                return true;
            case 2:
                printReport();
                return true;
            case 3:
                return false;
            default:
                return false;
        }
    }

    //添加学生
    public void addStudent() throws Exception {
        String inputString;
        Student student = new Student();
        Map<String,Integer> map = new HashMap<>();
        System.out.print("请输入学生信息" + STUDENT_INFO_FORMAT + "，按回车提交：\n");
        while (true) {
            inputString = reader.read(0);
            if (inputString == "invalid") {
                System.out.print("请按正确的格式输入" + STUDENT_INFO_FORMAT + "：\n");
            } else {
                String[] arr = inputString.split(",");
                for (int i = 0; i < arr.length; i++) {
                    if (i > 1) {
                        map.put(arr[i].split(":")[0].trim(), Integer.valueOf(arr[i].split(":")[1].trim()));
                    }
                }
                student.add(arr[0].trim(), Integer.parseInt(arr[1].trim()), map);
                //若添加相同学号学生，视为同一人，更新覆盖其信息
                if (students.stream().anyMatch(item -> item.getId() == student.getId())) {
                    students.stream().forEach(item -> {
                        if (item.getId() == student.getId()) {
                            item.add(arr[0].trim(), Integer.parseInt(arr[1].trim()), map);
                        }
                    });
                } else {
                    students.add(student);
                }
                System.out.print("学生" + student.getName() + "的成绩被添加\n");
                break;
            }
        }
    }

    //打印成绩单
    public void printReport() throws Exception {
        String inputString;
        System.out.println("请" + INPUT_ID_FORMAT);
        while (true) {
            inputString = reader.read(1);
            if (inputString == "invalid") {
                System.out.print("请按正确的格式" + INPUT_ID_FORMAT + "：\n");
            }else{
                String[] arr = inputString.split(",");
                List<Integer> summarys = new ArrayList<>();
                System.out.print("成绩单\n姓名|数学|语文|英语|编程|平均分|总分\n" + DIVIDER);
                Arrays.asList(arr).stream().forEach(item -> {
                    students.stream().filter(p -> p.getId() == Integer.parseInt(item.trim())).forEach(p -> {
                        summarys.add(Integer.valueOf(p.getSummary()));
                        System.out.print(p.printStudentInfo() + "\n");
                    });
                });
                System.out.print(DIVIDER);
                System.out.print("全班总分平均数：" + (summarys.isEmpty() ? 0 : getTotalAverage(summarys)) + "\n");
                System.out.print("全班总分中位数：" + (summarys.isEmpty() ? 0 : getTotalMidden(summarys)) + "\n") ;
                break;
            }
        }
    }

    //全班总分平均数
    public String getTotalAverage(List<Integer> summarys) {
        return removeZeroAfterDot(summarys.stream().mapToInt(item -> item).sum() / (double)summarys.size());
    }

    //全班总分中位数
    //中位数是排序后数组的中间值，如果数组的个数是偶数个，则返回排序后数组的第N/2个数
    public String getTotalMidden(List<Integer> summarys) {
        int index = summarys.size() % 2 == 0 ? summarys.size() / 2 - 1 : (summarys.size() - 1) / 2;
        return summarys.stream().sorted().toArray()[index].toString();
    }

    //去掉小数点后多余的0
    public String removeZeroAfterDot(double num){
        String arr = String.valueOf(num);
        return arr.replaceAll("0+$", "").replaceAll("[.]$", "");
    }

    public void initLibrary() throws Exception {
        while (printMainMenu()) {}
    }
}
