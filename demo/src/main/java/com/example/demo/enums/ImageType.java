package com.example.demo.enums;

public enum ImageType {
    QUESTION_IMAGE("question_images/"),
    OPTION1_IMAGE("option1_images/"),
    OPTION2_IMAGE("option2_images/"),
    OPTION3_IMAGE("option3_images/"),
    OPTION4_IMAGE("option4_images/"),
    SOLUTION_IMAGE("solution_images/");

    private final String folder;

    ImageType(String folder) {
        this.folder = folder;
    }

    public String getFolder() {
        return folder;
    }

    public static ImageType fromInt(int type) {
        switch (type) {
            case 1: return QUESTION_IMAGE;
            case 2: return OPTION1_IMAGE;
            case 3: return OPTION2_IMAGE;
            case 4: return OPTION3_IMAGE;
            case 5: return OPTION4_IMAGE;
            case 6: return SOLUTION_IMAGE;
            default: throw new IllegalArgumentException("Invalid type of image: " + type);
        }
    }
}

