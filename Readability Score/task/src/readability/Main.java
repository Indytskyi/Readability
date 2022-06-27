package readability;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {
        String s = "";
        File file = new File(args[0]);
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNext()) {
                s += scanner.nextLine();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

      //  s = "This is the front page of the Simple English Wikipedia. Wikipedias are places where people work together to write encyclopedias in different languages. We use Simple English words and grammar here. The Simple English Wikipedia is for everyone! That includes children and adults who are learning English. There are 142,262 articles on the Simple English Wikipedia. All of the pages are free to use. They have all been published under both the Creative Commons License and the GNU Free Documentation License. You can help here! You may change these pages and make new pages. Read the help pages and other good pages to learn how to write pages here. If you need help, you may ask questions at Simple talk. Use Basic English vocabulary and shorter sentences. This allows people to understand normally complex terms or phrases.";
        String[] strings = s.split("[.!?]");
        int characters = s.replaceAll(" |\n|\t", "").split("").length;
        int words = s.split(" |\n|\t").length;
        int sentences = s.split("\\.|\\?|!").length;
        int syllables = 0;
        int polysyllables = 0;
        for (String a : s.split(" |\n|\t")) {
            int syllablesOfOneWord = syllables(a);
            if (syllablesOfOneWord > 2) {
                polysyllables++;
            }
            syllables += syllablesOfOneWord;
        }

        System.out.printf("Words: %s\n", words);
        System.out.printf("Sentences: %s\n", sentences);
        System.out.printf("Characters: %s\n", characters);
        System.out.printf("Syllables: %s\n", syllables);
        System.out.printf("Polysyllables: %s\n", polysyllables);

        System.out.print("Enter the score you want to calculate (ARI, FK, SMOG, CL, all): ");
        Scanner scanner = new Scanner(System.in);
        String action = scanner.nextLine();
        System.out.println();
        double finalScore = 0;
        switch (action) {
            case "ARI":
                ARI(words, sentences, characters);
                break;
            case "FK":
                fleschKincaid(words, sentences, syllables);
                break;
            case "SMOG":
                SMOG(polysyllables, sentences);
                break;
            case "CL":
                CL(sentences, words, characters);
                break;
            case "all":
                ARI(words, sentences, characters);
                fleschKincaid(words, sentences, syllables);
                SMOG(polysyllables, sentences);
                CL(sentences, words, characters);
                break;
        }
    }

    public static void ARI(int words, int sentences, int characters) {
        double score = 4.71 * ((double) characters / (double) words);
        score += 0.5 * (double) words / (double) sentences - 21.43;
        System.out.printf("Automated Readability Index: %.2f (about %s-year-olds).\n", score, scores(score));
    }

    public static void fleschKincaid(int words, int sentences, int syllables) {
        double score = 0.39 * ((double) words / (double) sentences) + 11.8 * ((double) syllables / (double) words) - 15.59;
        System.out.printf("Flesch–Kincaid readability tests: %.2f (about %s-year-olds).\n", score, scores(score));
    }

    public static void SMOG(int polysyllables, int sentences) {
        double score = 1.043 * Math.sqrt(polysyllables * ((30 / (double) sentences))) + 3.1291;
        System.out.printf("Simple Measure of Gobbledygook: %.2f (about %s-year-olds).\n", score, scores(score));
    }

    public static void CL(int sentence, int words, int characters) {
        double s = (double) sentence / (double) words * 100;
        double l = (double) characters / (double) words * 100;
        double score = 0.0588 * l - 0.296 * s - 15.8;
        System.out.printf("Coleman–Liau index: %.2f (about %s-year-olds).\n", score, scores(score));
    }

    public static String scores(double score) {
        int number = (int) Math.round(score);
        String part = "";
        switch (number) {
            case 1:
                part = "6";
                break;
            case 2:
                part = "7";
                break;
            case 3:
                part = "9";
                break;
            case 4:
                part = "10";
                break;
            case 5:
                part = "11";
                break;
            case 6:
                part = "12";
                break;
            case 7:
                part = "13";
                break;
            case 8:
                part = "14";
                break;
            case 9:
                part = "15";
                break;
            case 10:
                part = "16";
                break;
            case 11:
                part = "17";
                break;
            case 12:
                part = "18";
                break;
            case 13:
                part = "24";
                break;
            case 14:
                part = "24+";
                break;
        }
        return part;

    }

    public static void stage2(int words, int sentences) {
        if (words / sentences > 10) {
            System.out.println("HARD");
        } else {
            System.out.println("EASY");
        }
    }

    public static int syllables(String s) {

        final Pattern p = Pattern.compile("([ayeiou]+)");
        final String lowerCase = s.toLowerCase();
        final Matcher m = p.matcher(lowerCase);
        int count = 0;
        while (m.find())
            count++;

        if (lowerCase.endsWith("e"))
            count--;


        return count < 0 ? 1 : count;
//        String[] words = s.replaceAll("e\\b", "").replaceAll("le", "a").replaceAll("[aeiouy]{2,}", "a").replaceAll("[0-9]+", "a").split("\\s");
//        int count  = 0;
//        char[] chars = words[0].toCharArray();
//        for (int i = 0; i < chars.length; i++) {
//            if (chars[i] == 'a' || chars[i] == 'e' || chars[i] == 'i' || chars[i] == 'o' || chars[i] == 'u'  || chars[i] == 'y') {
//                count++;
//            }
//        }
//        return count;
    }


}
