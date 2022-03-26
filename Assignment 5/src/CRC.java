import java.util.Scanner;

public class CRC
{
    public static void main(String[] args)
    {
        Scanner scan = new Scanner(System.in);
        int n;

        //Accept the input
        System.out.println("Enter the size of the data:");
        n = scan.nextInt();

        int[] data = new int[n];
        System.out.println("\nEnter the data, bit by bit:");

        for(int i=0 ; i < n ; i++)
        {
            System.out.println("Enter bit number " + (n-i) + ":");
            data[i] = scan.nextInt();
        }

        // Accept the divisor
        System.out.println("\n\n\nEnter the size of the divisor:");
        n = scan.nextInt();

        int[] divisor = new int[n];
        System.out.println("\nEnter the divisor, bit by bit:");
        for(int i=0 ; i < n ; i++)
        {
            System.out.println("Enter bit number " + (n-i) + ":");
            divisor[i] = scan.nextInt();
        }

        int[] remainder = divide(data, divisor);

        for(int i=0 ; i < remainder.length-1 ; i++)
        {
            System.out.print(remainder[i]);
        }

        System.out.println("\n\n\nThe CRC code generated is:");

        for (int datum : data)
        {
            System.out.print(datum);
        }

        for(int i=0 ; i < remainder.length-1 ; i++)
        {
            System.out.print(remainder[i]);
        }

        System.out.println();

        int[] sent_data = new int[data.length + remainder.length - 1];
        System.out.println("\n\n\nEnter the data to be sent:");

        for(int i=0 ; i < sent_data.length ; i++)
        {
            System.out.println("Enter bit number " + (sent_data.length-i) + ":");
            sent_data[i] = scan.nextInt();
        }

        // receive(sent_data, divisor);

        scan.close();
    }


    static int[] divide(int[] old_data, int[] divisor)
    {
        int[] remainder;
        int[] data = new int[old_data.length + divisor.length];

        System.arraycopy(old_data, 0, data, 0, old_data.length);
        remainder = new int[divisor.length];
        System.arraycopy(data, 0, remainder, 0, divisor.length);


        for(int i=0 ; i < old_data.length ; i++)
        {
            System.out.println((i+1) + ".) First data bit is : " + remainder[0]);
            System.out.print("Remainder : ");

            if(remainder[0] == 1)
            {
                // We have to xor the remainder bits with divisor bits
                for(int j=1 ; j < divisor.length ; j++)
                {
                    remainder[j-1] = exor(remainder[j], divisor[j]);
                    System.out.print(remainder[j-1]);
                }
            }

            else
            {
                // We have to exor the remainder bits with 0

                for(int j=1 ; j < divisor.length ; j++)
                {
                    remainder[j-1] = exor(remainder[j], 0);
                    System.out.print(remainder[j-1]);
                }
            }

            remainder[divisor.length-1] = data[i+divisor.length];
            System.out.println(remainder[divisor.length-1]);
        }

        return remainder;
    }


    // This function returns the exor of two bits
    static int exor(int a, int b)
    {
        if(a == b)
        {
            return 0;
        }
        return 1;
    }


    // Checks if the Data Sent was Correct or not
    // This is the receiver method
    // It accepts the data and divisor (although the receiver already has
    // the divisor value stored, with no need for the sender to resend it)
    static void receive(int[] data, int[] divisor)
    {
        int[] remainder = divide(data, divisor);

        for (int j : remainder)
        {
            if (j != 0)
            {
                System.err.println("There is an error in received data...");
                return;
            }
        }

        System.out.println("Data was received without any error.");
    }
}