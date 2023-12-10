package edu.project4.model;


public enum Format {
    JPEG, BMP, PNG;

    public String getType() {
        return switch (this) {
            case JPEG -> "jpeg";
            case BMP -> "bmp";
            case PNG -> "png";
        };
    }
}
