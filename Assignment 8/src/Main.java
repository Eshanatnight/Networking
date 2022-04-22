public class Main
{
    public static void main(String[] args)
    {
        var code = new HammingCode();
        code.run();

    }
}

class HammingCode
{
    static void print(int[] buffer)
    {
        for(int i = 1; i < buffer.length; i++)
        {
            System.out.print(buffer[i]);
        }

        System.out.println();
    }

    static int[] calculateCode(int[] arr, int r)
    {
        for(int i = 0; i < r; ++i)
        {
            int x = (int)Math.pow(2, i);

            for (int j = 1; j < arr.length; j++)
            {
                if (((j >> i) & 1) == 1 && x != j)
                {
                    arr[x] = arr[x] ^ arr[j];
                }
            }

            System.out.println("r" + x + " = " + arr[x]);
        }

        return arr;
    }

    static int[] generateCode(String str, int str_length, int r)
    {
        int[] arr = new int[r + str_length + 1];
        int j = 0;

        for (int i = 1; i < arr.length; i++)
        {
            if ((Math.ceil(Math.log(i) / Math.log(2)) - Math.floor(Math.log(i) / Math.log(2))) == 0)
            {
                //if i == 2^n for n in (0, 1, 2, .....)
                // then ar[i]=0
                // codeword[i] = 0 ----
                // redundant bits are initialized
                // with value 0
                arr[i] = 0;
            }

            else
            {
                // codeword[i] = dataword[j]
                arr[i] = (str.charAt(j) - '0');
                j++;
            }
        }

        return arr;
    }

    public void run()
    {
        String message = "0101";
        int message_length = message.length();

        // Number of redundant bits
        int redundent_bits = 1;

        while (Math.pow(2, redundent_bits) < (message_length + redundent_bits + 1))
        {
            redundent_bits++;
        }

        int[] ar = generateCode(message, message_length, redundent_bits);

        System.out.print("Generated Hamming Code:\n");

        ar = calculateCode(ar, redundent_bits);

        print(ar);
    }
}