package mypackage;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageProcessor {
    public static void processAndDisplayImage() {
        try {
            File selectedFile = ImageService.fileFinder();
            if (selectedFile != null) {
                BufferedImage originalImage = ImageIO.read(selectedFile);
                System.out.println("Selected File: " + selectedFile.getAbsolutePath());

                // Save the image
                ImageService.saveImage(selectedFile);

                // Ask for chop size in a non-blocking way
                String input = JOptionPane.showInputDialog("Enter chop size (2 to 6):");
                if (input == null) return; // Cancelled input

                int n;
                try {
                    n = Integer.parseInt(input);
                    if (n < 2 || n > 6) throw new NumberFormatException();
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Invalid input. Enter a number between 2 and 6.");
                    return;
                }

                BufferedImage resizedImage = resizeImage(originalImage, 600, 600);
                BufferedImage croppedImage = cropImageToFit(resizedImage, n);
                BufferedImage[][] imagePieces = chopImage(croppedImage, n);

                if (imagePieces != null) {
                    displayChoppedImages(imagePieces, n);
                }
            } else {
                System.out.println("No image selected.");
            }
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
    /*public static void main(String[] args) {
        try {
            File selectedFile = ImageService.fileFinder();
            if (selectedFile != null) {
                BufferedImage originalImage = ImageIO.read(selectedFile);
                System.out.println("Selected File: " + selectedFile.getAbsolutePath());
                ImageService.saveImage(selectedFile);
                System.out.println("Image saved successfully.");
                processImage(originalImage);
            } else {
                System.out.println("No image selected.");
            }
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    private static void processImage(BufferedImage originalImage) {
        String input = JOptionPane.showInputDialog("Enter chop size (2 to 6):");
        if (input == null) return;

        int n;
        try {
            n = Integer.parseInt(input);
            if (n < 2 || n > 6) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Enter a number between 2 and 6.");
            return;
        }

        BufferedImage resizedImage = resizeImage(originalImage, 600, 600);
        BufferedImage croppedImage = cropImageToFit(resizedImage, n);
        BufferedImage[][] imagePieces = chopImage(croppedImage, n);
        if (imagePieces != null) {
            displayChoppedImages(imagePieces, n);
        }
    }*/

    public static BufferedImage resizeImage(BufferedImage image, int targetWidth, int targetHeight) {
        BufferedImage resized = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = resized.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.drawImage(image, 0, 0, targetWidth, targetHeight, null);
        g2d.dispose();
        return resized;
    }

    public static BufferedImage cropImageToFit(BufferedImage image, int n) {
        int width = image.getWidth();
        int height = image.getHeight();
        int newWidth = (width / n) * n;
        int newHeight = (height / n) * n;
        int xOffset = (width - newWidth) / 2;
        int yOffset = (height - newHeight) / 2;
        return image.getSubimage(xOffset, yOffset, newWidth, newHeight);
    }

    public static BufferedImage[][] chopImage(BufferedImage image, int n) {
        int width = image.getWidth();
        int height = image.getHeight();
        int pieceWidth = width / n;
        int pieceHeight = height / n;

        BufferedImage[][] imagePieces = new BufferedImage[n][n];

        File outputDir = new File("chopped_images");
        if (!outputDir.exists()) {
            outputDir.mkdirs();
        }

        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                imagePieces[row][col] = image.getSubimage(col * pieceWidth, row * pieceHeight, pieceWidth, pieceHeight);
                File outputFile = new File(outputDir, "piece_" + row + "_" + col + ".png");
                try {
                    ImageIO.write(imagePieces[row][col], "png", outputFile);
                    System.out.println("Saved: " + outputFile.getAbsolutePath());
                } catch (IOException e) {
                    System.out.println("Error saving image piece: " + e.getMessage());
                }
            }
        }

        System.out.println("Chopping complete! Images stored in 'chopped_images' folder.");
        return imagePieces;
    }

    public static void displayChoppedImages(BufferedImage[][] imagePieces, int n) {
        JFrame frame = new JFrame("Chopped Image Display");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                int gap = 5;
                int pieceWidth = imagePieces[0][0].getWidth();
                int pieceHeight = imagePieces[0][0].getHeight();
                setBackground(Color.WHITE);

                for (int row = 0; row < n; row++) {
                    for (int col = 0; col < n; col++) {
                        if (imagePieces[row][col] != null) {
                            int x = col * (pieceWidth + gap);
                            int y = row * (pieceHeight + gap);
                            g.drawImage(imagePieces[row][col], x, y, pieceWidth, pieceHeight, this);
                        }
                    }
                }
            }
        };

        int panelWidth = n * (imagePieces[0][0].getWidth() + 5);
        int panelHeight = n * (imagePieces[0][0].getHeight() + 5);
        panel.setPreferredSize(new Dimension(panelWidth, panelHeight));

        JScrollPane scrollPane = new JScrollPane(panel);
        frame.add(scrollPane);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
