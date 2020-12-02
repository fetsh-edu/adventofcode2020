package me.fetsh.adventofcode2020.day2;

import me.fetsh.adventofcode2020.utils.File;
import java.io.IOException;
import java.util.Arrays;

class PolicyWithPassword {
    public int int1;
    public int int2;
    public char _char;
    public String password;

    public PolicyWithPassword(String[] ary) {
        this.int1 = Integer.parseInt(ary[0]);
        this.int2 = Integer.parseInt(ary[1]);
        this._char = ary[2].charAt(0);
        this.password = ary[3];
    }
    public Boolean valid1() {
        long occurrences = password.chars().filter(s -> s == _char).count();
        return occurrences >= int1 && occurrences <= int2;
    }
    public Boolean valid2() {
        return password.charAt(int1 - 1) == _char ^ password.charAt(int2 - 1) == _char;
    }
}

public class Main {
    public static void main(String[] args) throws IOException {
        var policyLines = File.readAllBytes(Main.class.getResource("input.txt").getPath()).split("\n");
        var policyWithPasswords = Arrays.stream(policyLines).map(s -> new PolicyWithPassword(s.split(":\s+|-|\s+"))).toArray(PolicyWithPassword[]::new);

        System.out.println(Arrays.stream(policyWithPasswords).filter(PolicyWithPassword::valid1).count());
        System.out.println(Arrays.stream(policyWithPasswords).filter(PolicyWithPassword::valid2).count());
    }
}