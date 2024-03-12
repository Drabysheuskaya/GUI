import java.util.*;
import java.util.List;
import java.awt.*;
import javax.swing.*;
import java.awt.geom.GeneralPath;

public class Main extends JFrame {

    public static void main(String[] args) {
        new Main(String.join(" ", args));
    }

    Main(String text) {

        MyCharacter[] secret = new MyCharacter[text.length()];

        Canopy canopy = new Canopy();

        char[] chars = text.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            secret[i] = new MyCharacter(chars[i]);
        }

        canopy.Line = new ArrayList<>();
        canopy.Line.add(new Color[80]);
        canopy.Line.add(new Color[80]);
        canopy.Line.add(new Color[80]);
        canopy.Line.add(new Color[80]);

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 80; j++) {
                canopy.Line.get(i)[j] = new Color(95,62,82,255);
            }
        }

        int c = 0;
        int p = 0;
        int w = 0;
        for (int i = 0; i < secret.length; i++) {

            if (secret[i].GetDigit() == 32) {
                c = p - 3;
                w++;
                continue;
            }

            for (int j = 0; j < 3; j++) {
                canopy.Line.get(w)[c] = new Color(96,102,122,255);
                c++;
                if (c >= 80) {
                    c = 0;
                }
            }

            for (int j : secret[i].GetBinary()) {
                if (j == 0) {
                    canopy.Line.get(w)[c] = new Color(255, 255, 255, 255);
                }
                else {
                    canopy.Line.get(w)[c] = new Color(255, 0, 0, 255);
                }
                c++;
                if (c >= 80) {
                    c = 0;
                }
            }

            if (i == secret.length-1 || secret[i +1].GetDigit() == 32) {
                for (int j = 0; j < 3; j++) {
                    canopy.Line.get(w)[c] = new Color(96,102,122,255);
                    c++;
                    if (c >= 80) {
                        c = 0;
                    }
                }
            }

            p = c;
        }

        this.setSize(1000,1000);

        this.setVisible(true);

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        this.setBackground(new Color(4, 5, 6, 255));

        this.setContentPane(canopy);
    }
}

class MyCharacter {

    private int Digit;

    private int[] Binary;

    public int GetDigit() {
        return Digit;
    }

    public int[] GetBinary() {
        return Binary;
    }

    MyCharacter(char character) {

        if (Character.isDigit(character)) {
            this.Digit = Character.getNumericValue(character);
        }
        else {
            this.Digit = Helper.findIndex(character);
        }

        this.Binary = Helper.findBytes(this.Digit);
    }
}

class Helper {
    public static int findIndex(char c) {
        if(c >= 'A' && c <= 'Z')  {
            return ((int)c - 'A' + 1);
        }
        if(c >= 'a' && c <= 'z') {
            return ((int)c - 'a' + 1);
        }
        return c;
    }

    public static int[] findBytes(int n) {
        int[] arr = new int[7];
        for (int i = 0; i < 7; i++) {
            int a = n >> i;
            if ((a & 1) > 0) {
                arr[i] = 1;
            }
            else {
                arr[i] = 0;
            }
        }

        int tmp;
        int l = arr.length;
        for (int i = 0; i < l / 2; i++) {
            tmp = arr[i];
            arr[i] = arr[l - i - 1];
            arr[l - i - 1] = tmp;
        }
        return arr;
    }
}

class Canopy extends JPanel {

    public List<Color[]> Line;

    void newCircle(Graphics2D g) {

        g.setPaint(new Color(4, 5, 6, 255));

        g.setStroke(new BasicStroke(0.5f));

        GeneralPath gp;
        
        List<double[]> points1 = new ArrayList<>();
        List<double[]> points2 = new ArrayList<>();
        List<double[]> points3 = new ArrayList<>();
        List<double[]> points4 = new ArrayList<>();
        List<double[]> points5 = new ArrayList<>();
        List<double[]> points6 = new ArrayList<>();

        for (int i = 0; i < 80; i++) {

            double f = i / 80.0 * Math.PI * 2;

            double x1 = 50 * Math.cos(f) + 500;
            double y1 = 50 * Math.sin(f) + 500;
            points1.add(new double[] {x1, y1});

            int r2;
            if (i % 2 == 0) {
                r2 = 150;
            } else {
                r2 = 170;
            }
            double x2 = r2 * Math.cos(f) + 500;
            double y2 = r2 * Math.sin(f) + 500;
            points2.add(new double[] {x2, y2});

            int r3;
            if (i % 2 == 0) {
                r3 = 280;
            } else {
                r3 = 300;
            }
            double x3 = r3 * Math.cos(f) + 500;
            double y3 = r3 * Math.sin(f) + 500;
            points3.add(new double[] {x3, y3});

            double x4 = 380 * Math.cos(f) + 500;
            double y4 = 380 * Math.sin(f) + 500;
            points4.add(new double[] {x4, y4});

            double x5 = 405 * Math.cos(f) + 500;
            double y5 = 405 * Math.sin(f) + 500;
            points5.add(new double[] {x5, y5});

            double x6 = 435 * Math.cos(f) + 500;
            double y6 = 435 * Math.sin(f) + 500;
            points6.add(new double[] {x6, y6});
        }

        for (int i = 0; i < 80; i = i + 1) {
            gp = new GeneralPath();

            gp.moveTo(points5.get(i)[0], points5.get(i)[1]);
            
            gp.lineTo(points6.get(i)[0], points6.get(i)[1]);

            if (i < points6.size() - 1) {
                gp.lineTo(points6.get(i+1)[0], points6.get(i+1)[1]);
            }
            else {
                gp.lineTo(points6.get(0)[0], points6.get(0)[1]);
            }

            if (i < points6.size() - 1) {
                gp.lineTo(points5.get(i+1)[0], points5.get(i+1)[1]);
            }
            else {
                gp.lineTo(points5.get(0)[0], points5.get(0)[1]);
            }

            gp.lineTo(points5.get(i)[0], points5.get(i)[1]);

            gp.closePath();
            g.setPaint(Line.get(3)[i]);
            g.fill(gp);
            g.setPaint(new Color(4, 5, 6, 255));
            g.draw(gp);
        }


        for (int i = 0; i < 80; i = i + 1) {
            gp = new GeneralPath();

            gp.moveTo(500, 500);

            gp.lineTo(points4.get(i)[0], points4.get(i)[1]);

            if (i < points4.size() - 1) {
                gp.lineTo(points4.get(i+1)[0], points4.get(i+1)[1]);
            }
            else {
                gp.lineTo(points4.get(0)[0], points4.get(0)[1]);
            }

            gp.lineTo(500, 500);

            gp.closePath();
            g.setPaint(Line.get(2)[i]);
            g.fill(gp);
            g.setPaint(new Color(4, 5, 6, 255));
            g.draw(gp);
        }

        for (int i = 0; i < 80; i = i + 1) {
            gp = new GeneralPath();

            gp.moveTo(500, 500);

            gp.lineTo(points3.get(i)[0], points3.get(i)[1]);

            if (i < points3.size() - 1) {
                gp.lineTo(points3.get(i+1)[0], points3.get(i+1)[1]);
            }
            else {
                gp.lineTo(points3.get(0)[0], points3.get(0)[1]);
            }

            gp.lineTo(500, 500);

            gp.closePath();
            g.setPaint(Line.get(1)[i]);
            g.fill(gp);
            g.setPaint(new Color(4, 5, 6, 255));
            g.draw(gp);
        }

        for (int i = 0; i < 80; i = i + 1)
        {
            gp = new GeneralPath();

            gp.moveTo(500, 500);

            gp.lineTo(points2.get(i)[0], points2.get(i)[1]);

            if (i < points2.size() - 1) {
                gp.lineTo(points2.get(i+1)[0], points2.get(i+1)[1]);
            }
            else {
                gp.lineTo(points2.get(0)[0], points2.get(0)[1]);
            }

            gp.lineTo(500, 500);

            gp.closePath();
            g.setPaint(Line.get(0)[i]);
            g.fill(gp);
            g.setPaint(new Color(4, 5, 6, 255));
            g.draw(gp);
        }

        GeneralPath center = new GeneralPath();

        center.moveTo(points1.get(0)[0], points1.get(0)[1]);

        for (int i = 0; i < 80; i = i + 1)
        {
            center.lineTo(points1.get(i)[0], points1.get(i)[1]);

            if (i < points1.size() - 1) {
                center.lineTo(points1.get(i+1)[0], points1.get(i+1)[1]);
            }
            else {
                center.lineTo(points1.get(0)[0], points1.get(0)[1]);
            }
        }

        center.closePath();
        g.setPaint(new Color(65,73,96,255));
        g.fill(center);
        g.setPaint(new Color(4, 5, 6, 255));
        g.draw(center);
    }

    @Override
    public void paint(Graphics gr) {
        Graphics2D g = (Graphics2D) gr;
        newCircle(g);
    }
}
