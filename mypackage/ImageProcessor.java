package mypackage;


import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ImageProcessor {
    public static ArrayList<ArrayList<BufferedImage>> processImage(File imageFile) {
        try {
            // Read the image from file
            BufferedImage originalImage = ImageIO.read(imageFile);
            if (originalImage == null) {
                JOptionPane.showMessageDialog(null, "Invalid image file.");
                return null;
            }
    
            // Ask for chop size
            String input = JOptionPane.showInputDialog("Enter chop size (2 to 6):");
            if (input == null) return null; // Cancelled input
    
            int n;
            try {
                n = Integer.parseInt(input);
                if (n < 2 || n > 6) throw new NumberFormatException();
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Invalid input. Enter a number between 2 and 6.");
                return null;
            }
    
            BufferedImage resizedImage = resizeImage(originalImage, 600, 600);
            BufferedImage croppedImage = cropImageToFit(resizedImage, n);
            return chopImage(croppedImage, n);
        } catch (IOException e) {
            System.err.println("Error reading image file: " + e.getMessage());
            return null;
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            return null;
        }
    }

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

    public static ArrayList<ArrayList<BufferedImage>> chopImage(BufferedImage image, int n) {
        int width = image.getWidth();
        int height = image.getHeight();
        int pieceWidth = width / n;
        int pieceHeight = height / n;

        ArrayList<ArrayList<BufferedImage>> imagePieces = new ArrayList<>();

        File outputDir = new File("chopped_images");

        // Clear the directory if it exists
        if (outputDir.exists()) {
            File[] files = outputDir.listFiles();
            if (files != null) {
                for (File file : files) {
                    file.delete();
                }
            }
        } else {
            outputDir.mkdirs();  // Create directory if it doesn't exist
        }

        for (int row = 0; row < n; row++) {
            imagePieces.add(new ArrayList<>());
            for (int col = 0; col < n; col++) {
                BufferedImage piece = image.getSubimage(col * pieceWidth, row * pieceHeight, pieceWidth, pieceHeight);
                imagePieces.get(row).add(piece);

                File outputFile = new File(outputDir, "piece_" + row + "_" + col + ".png");
                try {
                    ImageIO.write(piece, "png", outputFile);
                    System.out.println("Saved: " + outputFile.getAbsolutePath());
                } catch (IOException e) {
                    System.out.println("Error saving image piece: " + e.getMessage());
                }
            }
        }

        System.out.println("Chopping complete! Images stored in 'chopped_images' folder.");
        return imagePieces;
    }

    public static void displayChoppedImages(ArrayList<ArrayList<BufferedImage>> imagePieces) {
        if (imagePieces == null || imagePieces.isEmpty()) {
            System.out.println("No images to display.");
            return;
        }

        int n = imagePieces.size();

        JFrame frame = new JFrame("Chopped Image Display");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                int gap = 5;
                int pieceWidth = imagePieces.get(0).get(0).getWidth();
                int pieceHeight = imagePieces.get(0).get(0).getHeight();
                setBackground(Color.WHITE);

                for (int row = 0; row < n; row++) {
                    for (int col = 0; col < n; col++) {
                        if (imagePieces.get(row).get(col) != null) {
                            int x = col * (pieceWidth + gap);
                            int y = row * (pieceHeight + gap);
                            g.drawImage(imagePieces.get(row).get(col), x, y, pieceWidth, pieceHeight, this);
                        }
                    }
                }
            }
        };

        int panelWidth = n * (imagePieces.get(0).get(0).getWidth() + 5);
        int panelHeight = n * (imagePieces.get(0).get(0).getHeight() + 5);
        panel.setPreferredSize(new Dimension(panelWidth, panelHeight));

        JScrollPane scrollPane = new JScrollPane(panel);
        frame.add(scrollPane);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
