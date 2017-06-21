package org.jboss.test.clusterbench.common.load;

public class SendTrafficLoad {
    public static final byte[] oneKilobyte = {'A', 'A', 'X', 'A', 'A', 'B', '3', 'N', 'z', 'a', 'C', '1', 'y', 'c', '2', 'E', 'A', 'A', 'A', 'A', 'D', 'A', 'Q', 'A', 'B', 'A', 'A', 'A', 'B', 'A', 'Q', 'C', 'L', 'O', 'w', '3', 'g', '1', '8', '7', 'Y', 'K', 'e', 'I', 'q', 'b', 'n', 'A', 'P', '5', 'v', 'S', 'T', 'w', 'b', '2', '9', '8', '3', '3', 'Y', 'U', 'F', 'T', 'x', 'n', 'j', 'B', 'c', '0', 'G', 'y', 'b', '3', 'T', '8', 'q', 't', 'D', 'Y', 'n', '5', 'S', 'Q', 'g', 'k', 'w', 'P', '2', 'R', 'v', 'X', 't', '7', 'S', 'R', 's', 'z', 'Y', 'P', 's', '1', 'b', 'q', 'w', 'm', '3', 'W', 'b', 'u', 'j', 'F', 'O', 'm', 'R', 'Y', 'r', '8', 'K', 'E', 'N', '8', '3', 'W', 'Z', 'a', 'J', 'Z', 'P', 'w', 'g', '0', 'D', 'A', '1', 'j', 'R', 'L', 'O', 'n', 'm', 'W', 'z', 'J', '5', '1', 'Z', 'H', 'B', 'D', '1', 'S', 'l', '3', '1', 'z', 'v', 'N', 'A', '1', 'v', 'Z', 'i', 'g', 'O', 'V', 'U', 'L', 'e', '1', 'D', 'a', 'B', 'Z', 'r', 'C', 'D', 'Z', 'N', 'U', '7', 'X', 'X', 'l', 'L', 'p', '3', 'V', '1', 'z', '0', 'H', 'C', 'B', 'v', 'x', 'N', 'W', 'i', 'v', 'j', 'Z', 'm', 'g', 'H', 'Y', 'T', '7', '0', 's', 'U', '4', 'u', 'S', 'v', 'a', 'I', 'T', '7', 'f', 'B', 'Y', '5', 'm', 'Q', '7', 'K', 'D', 'H', 'S', 'C', 'p', 'K', 'i', 'k', 'r', 'C', 's', 'm', 'X', '7', 'd', 'C', 's', 'E', 'S', 'K', 'Z', 'a', 'l', 'k', '1', 'l', 'i', 'Y', 'C', 'X', 'n', 'u', 'I', 'd', 'q', 'W', 'r', 'J', 'm', 'w', '2', '8', 's', '3', 'D', 'f', 'W', '6', 'M', 'Z', 'Q', 'G', 'm', 'U', 'n', 'G', 'a', 'z', 'x', 'M', 'O', 'u', 'Q', 'W', 'a', 'K', 'P', 'p', 'N', 'V', 'N', 'w', 'E', 'R', 'F', 's', 'X', 'W', 'd', 'h', 'J', '1', 'D', 'x', 'F', 'D', '3', '9', 'O', 'n', 'Z', 'M', 'T', 'Q', '1', 'X', 'T', '8', 'd', 'Z', 'B', 'J', 'U', 'y', 'B', 'w', 'Y', 'e', '7', 'k', 'Q', 'e', 'i', 'N', 'I', 'M', 'V', 'R', 'x', '2', 's', 'Q', 'U', 'b', 'c', '1', 'S', 'L', 'Y', 'j', 'l', '4', '5', 'n', 'o', 's', 'P', 'Y', 'U', 'W', 'g', '7', 'b', 'H', 'A', 'A', 'A', 'A', 'B', '3', 'N', 'z', 'a', 'C', '1', 'y', 'c', '2', 'E', 'A', 'A', 'A', 'A', 'D', 'A', 'Q', 'A', 'B', 'A', 'A', 'A', 'B', 'A', 'Q', 'C', 's', 'L', 'O', 'w', '3', 'g', '1', '8', '7', 'Y', 'K', 'e', 'I', 'q', 'b', 'n', 'A', 'P', '5', 'v', 'S', 'T', 'w', 'b', '2', '9', '8', '3', '3', 'Y', 'U', 'F', 'T', 'x', 'n', 'j', 'B', 'c', '0', 'G', 'y', 'b', '3', 'T', '8', 'q', 't', 'D', 'Y', 'n', '5', 'S', 'Q', 'g', 'k', '3', 'w', 'P', '2', 'R', 'v', 'X', 't', '7', 'S', 'R', 'e', 'w', 's', 'X', 'X', 'q', 'q', 'z', 'Y', 'P', 's', '1', 'b', 'q', 'w', 'm', '3', 'W', 'b', 'u', 'j', 'F', 'O', 'm', 'R', 's', 's', 's', 's', 's', 's', 'Y', 'r', '8', 'K', 'E', 'N', '8', '3', 'W', 'Z', 'a', 'J', 'Z', 'P', 'w', 'g', '0', 'D', 'A', '1', 'j', 'R', 'L', 'O', 'n', 'm', 'W', 'z', 'J', '5', '1', 'Z', 'H', 'B', 'D', '1', 'S', 'l', '3', '1', 'z', 'v', 'N', 'A', '1', 'v', 'Z', 'i', 'g', 'O', 'V', 'U', 'L', 'e', '1', 'D', 'a', 'B', 'Z', 'r', 'C', 'D', 's', 's', 's', 's', 's', 'Z', 'N', 'U', '7', 'X', 'X', 'l', 'L', 'p', '3', 'V', '1', 'z', '0', 'H', 'C', 'B', 'v', 'x', 'N', 'W', 'i', 'v', 'j', 'Z', 'm', 'g', 'H', 'Y', 'T', '7', '0', 's', 'U', '4', 'u', 'S', 'v', 'a', 'I', 'T', '7', 'f', 'B', 'Y', '5', 'm', 'Q', '7', 'K', 'D', 'H', 'S', 'C', 'p', 'K', 'i', 'k', 'r', 'C', 's', 'm', 'X', '7', 'd', 'C', 's', 'E', 'S', 'K', 'Z', 'a', 'l', 'k', '1', 'l', 'i', 'Y', 'C', 'X', 'n', 'u', 'I', 'd', 'q', 'W', 'r', 'J', 'm', 'w', '2', '8', 's', '3', 'D', 'f', 'W', '6', 'M', 'Z', 'Q', 'G', 'm', 'U', 'n', 'G', 'a', 'z', 'x', 'M', 'O', 'u', 'Q', 'W', 'a', 'K', 'P', 'e', 'r', 'h', 'p', 'N', 'V', 'N', 'w', 'E', 'R', 'F', 's', 'X', 'W', 'd', 'h', 'J', '1', 'D', 'x', 'F', 'D', '3', '9', 'O', 'n', 'Z', 'M', 'T', 'Q', '1', 'X', 'T', '8', 'd', 'Z', 'B', 'J', 'U', 'y', 'B', 'w', 'Y', 'e', '7', 'k', 'Q', 'e', 'i', 'N', 'I', 'M', 'V', 'R', 'x', '2', 's', 'Q', 'U', 'b', 'c', '1', 'S', 'L', 'Y', 'j', 'l', '4', '5', 'n', 'o', 's', 'P', 'Y', 'U', 'W', 'g', '7', 'b', 'H', 'A', 'A', 'A', 'A', 'B', '3', 'N', 'z', 'a', 'C', '1', 'y', 'c', '2', 'E', 'A', 'A', 'A', 'A', 'D', 'A', 'Q', 'A', 'B', 'A', 'A', 'A', 'B', 'A', 'Q', 'C', 's', 'L', 'O', 'w', '3', 'g', '1', '8', '7', 'Y', 'K', 'e', 'I', 'q', 'b', 'n', 'A', 'P', '5', 'v', 'S', 'T', 'w', 'b', '2', '9', '8', '3', '3', 'Y', 'U', 'F', 'T', 'x', 'n', 'j', 'B', 'c', '0', 'G', 'y', 'b', '3', 'T', '8', 'q', 't', 'D', 'Y', 'n', '5', 'S', 'Q', 'g', 'k', '3', 'w', 'P', '2', 'R', 'v', 'X', 't', '7', 'S', 'R', 's', 'z', 'Y', 'P', 's', '1', 'b', 'q', 'w', 'm', '3', 'W', 'b', 'u', 'j', 'F', 'O', 'm', 'R', 'Y', 'r', '8', 'K', 'E', 'N', '8', '3', 'W', 'Z', 'a', 'J', 'Z', 'P', 'w', 'g', '0', 'D', 'A', '1', 'j', 'R', 'L', 'O', 'n', 'm', 'W', 'z', 'J', '5', '1', 'Z', 'H', 'B', 'D', '1', 'S', 'l', '3', '1', 'z', 'v', 'N', 'A', '1', 'v', 'Z', 'i', 'g', 'O', 'V', 'U', 'L', 'e', '1', 'D', 'a', 'B', 'Z', 'r', 'C', 'D', 'Z', 'N', 'U', '7', 'X', 'X', 'l', 'L', 'p', '3', 'V', '1', 'z', '0', 'H', 'C', 'B', 'v', 'x', 'N', 'W', 'i', 'v', 'j', 'Z', 'm', 'g', 'H', 'Y', 'T', '7', '0', 's', 'U', '4', 'u', 'S', 'v', 'a', 'I', 'T', '7', 'f', 'B', 'Y', '5', 'm', 'Q', '7', 'K', 'D', 'H', 'S', 'C', 'p', 'K', 'i', 'k', 'r', 'C', 's', 'm', 'X', '7', 'd', 'C', 's', 'E', 'S', 'K', 'Z', 'a', 'l', 'k', '1', 'l', 'i', 'Y', 'C', 'X', 'd', 'h', 'q'};

    public String generateRubbish(int kilobytes) {
        byte[] bigArray = new byte[1024 * kilobytes];
        int currentOffset = 0;
        for (int i = 0; i < kilobytes; i++) {
            System.arraycopy(oneKilobyte, 0, bigArray, currentOffset, oneKilobyte.length);
            currentOffset += oneKilobyte.length;
        }
        return new String(bigArray);
    }

}
