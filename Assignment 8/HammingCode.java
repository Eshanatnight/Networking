import java.util.Scanner;

public class HammingCode
{
    int size;
    int hammingCodeSize;
    int errorPosition;
    int[] buffer;
    int[] hammingCode;

    Scanner consoleIn;

    private HammingCode()
    {
        consoleIn = new Scanner(System.in);
    }

    private void init()
    {
        System.out.print("Enter the bits size for the data: ");
        size = consoleIn.nextInt();

        buffer = new int[size];

        getData();
        System.out.println("Data: ");
        printbuffer(buffer);
    }

    // get data from user which we want to transfer
    private void getData()
    {
        System.out.print("Enter the data:");
        for(int j = 0 ; j < size ; j++)
        {
            System.out.println((size - j) + "bit: ");

            // fill array with user entered data
            buffer[size - j - 1] = consoleIn.nextInt();
        }
    }

    private void printbuffer(int[] buffer)
    {
        for(int i = 1; i < size; i++)
        {
            System.out.print(buffer[size - i - 1]);
        }

        System.out.println();
    }

    // getHammingCode method that returns the hamming code for the data which we want to send
    private static int[] getHammingCode(int[] data)
    {
        // declare an array that will store the hamming code for the data
        int[] returnData;

        int size;

        // code to get the required number of parity bits
        int i = 0;
        int j = 0;
        int k = 0;
        int parityBits = 0;

        size = data.length;

        while(i < size)
        {
            // 2 power of parity bits must equal to the current position
            // i.e. number of bits traversed + number of parity bits + 1

            if(Math.pow(2, parityBits) == (i + parityBits + 1))
            {
                parityBits++;
            }

            else
            {
                i++;
            }
        }

        // the size of the returnData would be
        // the size of the original data + the number of parity bits.
        returnData = new int[size + parityBits];

        // indicate an unset value in parity bit location with '2'
        for(i = 1; i <= returnData.length; i++)
        {
            // condition to find parity bit location
            if(Math.pow(2, j) == i)
            {
                returnData[(i - 1)] = 2;
                j++;
            }

            else
            {
                returnData[(k + j)] = data[k++];
            }
        }

        // use for loop to set even parity bits at parity bit locations
        for(i = 0; i < parityBits; i++)
        {
            returnData[((int) Math.pow(2, i)) - 1] = getParityBit(returnData, i);
        }

        return returnData;
    }

    private void setHammingCode()
    {
        hammingCode = getHammingCode(buffer);
    }

    // getParityBit method that return parity bit based on the power
    static int getParityBit(int[] returnData, int pow)
    {
        int parityBit = 0;
        int size = returnData.length;

        for(int i = 0; i < size; i++)
        {
            // check whether returnData[i] contains an unset value or not
            if(returnData[i] != 2)
            {
                // if not, we save the index in k by increasing 1 in its value
                int k = (i + 1);

                // convert the value of k into binary
                String str = Integer.toBinaryString(k);

                // check the value stored at that location. If the value is 1 or 0,
                // calculate the parity value.
                int temp = ((Integer.parseInt(str)) / ((int) Math.pow(10, pow))) % 10;

                if(temp == 1 && returnData[i] == 1)
                {
                    parityBit = (parityBit + 1) % 2;
                }
            }
        }

        return parityBit;
    }

    private void printHammingCode()
    {
        System.out.println("Hamming Code: ");

        for(int i = 0; i < hammingCode.length; i++)
        {
            System.out.print(hammingCode[hammingCode.length - i - 1]);
        }

        System.out.println();
    }


    private void testHammingCheck()
    {
        System.out.println("For detecting error at the reciever end," +
        " enter position of a bit to alter original data " +
        "(0 for no error):"
        );

        errorPosition = consoleIn.nextInt();

        // colosing the scanner here because we don't need it anymore
        consoleIn.close();

        // check whether if the entered position is 0
        if(errorPosition != 0)
        {
            // alter bit of the user entered position
            hammingCode[errorPosition - 1] = (hammingCode[errorPosition - 1] + 1) % 2;
        }

        // Print the Sent Data
        System.out.println("Sent Data is:");

        for(int k = 0; k < hammingCode.length; k++)
        {
            System.out.print(hammingCode[hammingCode.length - k - 1]);
        }

        System.out.println();


        receiveData(hammingCode, hammingCode.length - buffer.length);
    }


    // receiveData method to detect error in the received data
    static void receiveData(int[] data, int parityBits)
    {
        int size = data.length;

        // declare parityArray to store the value of parity check
        int[] parityArray = new int[parityBits];

        // we use errorLoc string for storing the integer value of the error location.
        String errorLoc = new String();

        // check the parities
        for(int pow = 0; pow < parityBits; pow++)
        {
            // use for loop to extract the bit from 2^(power)
            for(int i = 0; i < size; i++)
            {
                int j = i + 1;

                String str = Integer.toBinaryString(j);

                int bit = ((Integer.parseInt(str)) / ((int) Math.pow(10, pow))) % 10;

                if(bit == 1 && data[i] == 1)
                {
                    parityArray[pow] = (parityArray[pow] + 1) % 2;
                }
            }

            errorLoc = parityArray[pow] + errorLoc;
        }


        // check if there is a single bit error and then correct it.
        // errorLoc provides parity check eq. values if there is an error, correct it

        int finalLoc = Integer.parseInt(errorLoc, 2);

        // check whether the finalLoc value is 0 or not
        if(finalLoc != 0)
        {
            System.out.println("Error is found at location " + finalLoc + ".");

            data[finalLoc - 1] = (data[finalLoc - 1] + 1) % 2;

            System.out.println("After correcting the error, the code is:");

            for(int i = 0; i < size; i++)
            {
                System.out.print(data[size - i - 1]);
            }

            System.out.println();
        }
        else
        {
            System.out.println("There is no error in the received data.");
        }

        // print the original data
        System.out.println("The data sent from the sender:");

        int pow = parityBits - 1;
        for(int k = size; k > 0; k--)
        {
            if(Math.pow(2, pow) != k)
            {
                System.out.print(data[k - 1]);
            }
            else
            {
                pow--;
            }
        }

        System.out.println();
    }

    public static void main(String[] args)
    {

        var hc = new HammingCode();
        hc.init();

        hc.printbuffer(hc.buffer);

        hc.setHammingCode();

        hc.hammingCodeSize = hc.hammingCode.length;

        hc.printHammingCode();

        hc.testHammingCheck();
    }
}
