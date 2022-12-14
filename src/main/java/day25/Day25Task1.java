package day25;

public class Day25Task1 {
    public static void main(String[] args) {
        String[] lines = INPUT.split("\n");

        long sum = 0;
        for (int i = 0; i < lines.length; i++) {
            String line = lines[i];
            long v = toDecimal(line);
            sum += v;
            // System.out.println(v);
        }
        System.out.println("Decimal sum: " + sum);

        System.out.println("SNAFU sum: " + toSnafu(sum));
    }

    public static long toDecimal(String snafu) {
        int l = snafu.length();
        long mult = 1;
        long num = 0;
        for (int i = l - 1; i >= 0; i--) {
            num += mult * switch (snafu.charAt(i)) {
                case '0' -> 0;
                case '1' -> 1;
                case '2' -> 2;
                case '-' -> -1;
                case '=' -> -2;
                default -> throw new IllegalArgumentException();
            };
            mult *= 5;
        }
        return num;
    }

    public static String toSnafu(long decimal) {
        StringBuilder sb = new StringBuilder();
        while (decimal != 0) {
            decimal += 2;
            int v = Math.floorMod(decimal, 5);
            decimal = (decimal - v) / 5;
            sb.insert(0, switch (v) {
                case 4 -> '2';
                case 3 -> '1';
                case 2 -> '0';
                case 1 -> '-';
                case 0 -> '=';
                default -> throw new IllegalArgumentException();
            });
        }
        return sb.toString();
    }

    public static final String INPUT_TEST = "1=-0-2\n"
            + "12111\n"
            + "2=0=\n"
            + "21\n"
            + "2=01\n"
            + "111\n"
            + "20012\n"
            + "112\n"
            + "1=-1=\n"
            + "1-12\n"
            + "12\n"
            + "1=\n"
            + "122";

    public static final String INPUT = "10===1\n"
            + "1-2=1-2-===11-1\n"
            + "10-0\n"
            + "2==-10\n"
            + "12=10=0--01-0=000\n"
            + "120-==1\n"
            + "1-11-===0-\n"
            + "2-00=1=\n"
            + "1-010=1112==1-=\n"
            + "2=-=220--2=-=210\n"
            + "10-0-2-=\n"
            + "2=011120\n"
            + "1-0-1-1=0=202\n"
            + "1010=2021=0-==\n"
            + "11=20212010\n"
            + "1-==1\n"
            + "1=1-2==21-\n"
            + "1-==\n"
            + "22=2=0=120=\n"
            + "2-22020-2-2=-22\n"
            + "2-022-2-1112212\n"
            + "1==0\n"
            + "1222-\n"
            + "11==10\n"
            + "2221120-\n"
            + "1-1-2--0-2-120==2-=1\n"
            + "11101-\n"
            + "1-11=212-20\n"
            + "22=-2=-02=-=-01212\n"
            + "1=-\n"
            + "11=2=1-222-=-\n"
            + "1=12-22-=-21\n"
            + "1-=0212--=1=01=2\n"
            + "1220-12020-\n"
            + "22=010201\n"
            + "202-1020212220=0=0\n"
            + "21-201=2021=1--0200\n"
            + "2=--\n"
            + "2111=\n"
            + "20-02211112=0-\n"
            + "21-==20-22\n"
            + "2=221101-=\n"
            + "11-=-1210\n"
            + "10-222121=2-02\n"
            + "1-202-1-01==\n"
            + "101=11--0-=\n"
            + "10==110101121\n"
            + "2222--2-020\n"
            + "1-==2==02020\n"
            + "22001-20=2-2\n"
            + "1-001\n"
            + "202--=2=0-=12=\n"
            + "2=\n"
            + "1-2\n"
            + "1-=-1-==02=20\n"
            + "2==2=-12=110\n"
            + "20210-22-\n"
            + "1201---\n"
            + "10=0==10===-2---0\n"
            + "122\n"
            + "1-2-1-\n"
            + "12=2-=21\n"
            + "1-200000012\n"
            + "1===-=02=1-=-\n"
            + "122=0=2201\n"
            + "211-=112--21-==2=\n"
            + "1-1\n"
            + "1=0211-2\n"
            + "122-1-1=021-==-0\n"
            + "22=11-122-10\n"
            + "22=11\n"
            + "2210=2\n"
            + "20=-=-=11\n"
            + "1=0200-1-2=0\n"
            + "10=012-\n"
            + "1=-2=1-21=-021\n"
            + "21221=101-=\n"
            + "22=0=\n"
            + "1-==201--112-1200\n"
            + "2==2-==\n"
            + "1-0=1-1\n"
            + "201--101=021--00\n"
            + "12-1-111121012\n"
            + "11010-2-11--1-0-2\n"
            + "1101-0==210-2\n"
            + "1=0\n"
            + "21-01--\n"
            + "1=112=1-0-\n"
            + "1201=---\n"
            + "120--1---\n"
            + "1=0-0---0-\n"
            + "1-\n"
            + "2000010-2-12=10\n"
            + "1=20\n"
            + "211==112\n"
            + "102020=-=-1210=1-1\n"
            + "21==11020-021\n"
            + "2=-00====210=1\n"
            + "1-0=-=-21-00=0-\n"
            + "1=110---=0=2000=20=\n"
            + "1-122-=11=2-22-\n"
            + "120101-12=02=1\n"
            + "12-2-0==-001221=\n"
            + "22=1--10=\n"
            + "11100=--0-10--=02=\n"
            + "102-01\n"
            + "1=2001=2=-";
}
