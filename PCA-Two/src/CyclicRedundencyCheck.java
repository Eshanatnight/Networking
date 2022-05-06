import java.util.Scanner;

class CRC
{
    Scanner scan;
    int dataBuffSize;

    int[] data;
    int[] divisor;
    int[] remainder;

    private void initComponents()
    {
        scan = new Scanner(System.in);
        dataBuffSize = this.setDataBuffSize();
        data = new int[dataBuffSize];
    }


    private int setDataBuffSize()
    {
        System.out.println("Enter the size of the data:");
        return this.scan.nextInt();
    }

    // Accept the input
    private void getData()
    {
        System.out.println("\nEnter the data, bit by bit:");

        for(int i = 0 ; i < dataBuffSize; ++i)
        {
            System.out.println("Enter bit number " + (this.dataBuffSize - i) + ":");
            data[i] = scan.nextInt();
        }
    }

    // Accept the divisor
    private void setDivisor()
    {
        System.out.println("\n\n\nEnter the size of the divisor:");
        int divisorBuffSize = this.scan.nextInt();

        System.out.println("\nEnter the divisor, bit by bit:");
        divisor = new int[divisorBuffSize];

        for(int i = 0 ; i < divisorBuffSize; ++i)
        {
            System.out.println("Enter bit number " + (divisorBuffSize - i) + ":");
            divisor[i] = scan.nextInt();
        }
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
            System.out.println((i + 1) + ".) First data bit is : " + remainder[0]);
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

    private void printGeneratedCRC()
    {
        System.out.println("\n\n\nThe CRC code generated is:");

        for (int datum : this.data)
        {
            System.out.print(datum);
        }

        for(int i=0 ; i < this.remainder.length - 1 ; i++)
        {
            System.out.print(this.remainder[i]);
        }

        System.out.println();
    }

    private void printRemainder()
    {
        for(int i=0 ; i < remainder.length-1 ; i++)
        {
            System.out.print(remainder[i]);
        }
    }

    private void closeComponents()
    {
        scan.close();
    }


    public void run()
    {
        this.initComponents();
        this.getData();
        this.setDivisor();
        remainder = divide(this.data, this.divisor);

        this.printRemainder();
        this.printGeneratedCRC();

    }

    private void test(boolean test)
    {
        if(!test)
            return;

        int[] sent_data = new int[this.data.length + this.remainder.length - 1];
        System.out.println("\n\n\nEnter the data to be sent:");

        for(int i=0 ; i < sent_data.length ; i++)
        {
            System.out.println("Enter bit number " + (sent_data.length-i) + ":");
            sent_data[i] = this.scan.nextInt();
        }

        receive(this.data, this.divisor);
    }



    public static void main(String[] args)
    {
        CRC object = new CRC();
        object.run();

        object.test(true);
        object.closeComponents();

    }

}