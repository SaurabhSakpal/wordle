package com.wordle.solution;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Main {
    private static Map<Integer, String> wordsMap = new HashMap<>();
    private static CharacterMapping[] characterMapping = new CharacterMapping[26];

    public static void main(String[] args) {
        if (args.length != 3) {
            System.out.println("The program needs 3 argument");
        }
        for (int i = 0; i < characterMapping.length; i++) {
            characterMapping[i] = new CharacterMapping();
        }
        String[] dictionary = generateDictionary();
        for (int i = 0; i < dictionary.length; i++) {
            processWord(dictionary[i], i);
            wordsMap.put(i, dictionary[i]);
        }
        Set<Integer> solutionSet = new HashSet<>();
        processArguments(solutionSet, args);

        for (Integer index : solutionSet) {
            System.out.println(wordsMap.get(index));
        }
    }

    private static void processArguments(Set<Integer> solutionSet, String[] args) {
        processPositionArgument(solutionSet, args[0]);
        processContainsArgument(solutionSet, args[1]);
        processExclusionArgument(solutionSet, args[2]);
    }

    private static void processExclusionArgument(Set<Integer> solutionSet, String exclusionArg) {
        if (exclusionArg.equalsIgnoreCase("-")) {
            return;
        }
        for (int i = 0; i < exclusionArg.length(); i++) {
            solutionSet.removeAll(characterMapping[exclusionArg.charAt(i) - 97].wordMapping[0]);
            solutionSet.removeAll(characterMapping[exclusionArg.charAt(i) - 97].wordMapping[1]);
            solutionSet.removeAll(characterMapping[exclusionArg.charAt(i) - 97].wordMapping[2]);
            solutionSet.removeAll(characterMapping[exclusionArg.charAt(i) - 97].wordMapping[3]);
            solutionSet.removeAll(characterMapping[exclusionArg.charAt(i) - 97].wordMapping[4]);
        }
    }

    private static void processContainsArgument(Set<Integer> solutionSet, String containsArg) {
        if (containsArg.equalsIgnoreCase("-")) {
            return;
        }

        for (int i = 0; i < containsArg.length(); i++) {
            if (solutionSet.isEmpty()) {
                solutionSet.addAll(characterMapping[containsArg.charAt(i) - 97].wordMapping[0]);
                solutionSet.addAll(characterMapping[containsArg.charAt(i) - 97].wordMapping[1]);
                solutionSet.addAll(characterMapping[containsArg.charAt(i) - 97].wordMapping[2]);
                solutionSet.addAll(characterMapping[containsArg.charAt(i) - 97].wordMapping[3]);
                solutionSet.addAll(characterMapping[containsArg.charAt(i) - 97].wordMapping[4]);
            }
            else {
                Set<Integer> allWordsForCharacter = new HashSet<>();
                allWordsForCharacter.addAll(characterMapping[containsArg.charAt(i) - 97].wordMapping[0]);
                allWordsForCharacter.addAll(characterMapping[containsArg.charAt(i) - 97].wordMapping[1]);
                allWordsForCharacter.addAll(characterMapping[containsArg.charAt(i) - 97].wordMapping[2]);
                allWordsForCharacter.addAll(characterMapping[containsArg.charAt(i) - 97].wordMapping[3]);
                allWordsForCharacter.addAll(characterMapping[containsArg.charAt(i) - 97].wordMapping[4]);
                solutionSet.retainAll(allWordsForCharacter);
            }
        }
    }

    private static void processPositionArgument(Set<Integer> solutionSet, String positionArg) {
        if (positionArg.equalsIgnoreCase("-")) {
            return;
        }
        for (int i = 0; i < positionArg.length(); i++) {
            if ('*' != positionArg.charAt(i)) {
                if (solutionSet.isEmpty()) {
                    solutionSet.addAll(characterMapping[positionArg.charAt(i) - 97].wordMapping[i]);
                }
                else {
                    solutionSet.retainAll(characterMapping[positionArg.charAt(i) - 97].wordMapping[i]);
                }
            }
        }
    }

    private static String[] generateDictionary() {
        String[] dictionary = new String[15918];
        int count = 0;
        try (BufferedReader br = new BufferedReader(new FileReader("/Users/ssakpal/Desktop/five-letter-word.txt"))) {
            String word = br.readLine();
            while (word != null) {
                dictionary[count++] = word;
                word = br.readLine();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return dictionary;
    }

    private static void processWord(String word, Integer index) {
        characterMapping[word.charAt(0) - 97].wordMapping[0].add(index);
        characterMapping[word.charAt(1) - 97].wordMapping[1].add(index);
        characterMapping[word.charAt(2) - 97].wordMapping[2].add(index);
        characterMapping[word.charAt(3) - 97].wordMapping[3].add(index);
        characterMapping[word.charAt(4) - 97].wordMapping[4].add(index);
    }

    private static class CharacterMapping {
        private final HashSet[] wordMapping = new HashSet[5];

        CharacterMapping() {
            wordMapping[0] = new HashSet<Integer>();
            wordMapping[1] = new HashSet<Integer>();
            wordMapping[2] = new HashSet<Integer>();
            wordMapping[3] = new HashSet<Integer>();
            wordMapping[4] = new HashSet<Integer>();
        }
    }
}
