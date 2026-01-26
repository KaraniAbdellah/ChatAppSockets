package services;

public class Services {
    /**
     * Splits a string into two parts using the '>' delimiter.
     *
     * @param str   the input string (example: "Message>ClientNumber")
     * @param event determines which part to return:
     *              <ul>
     *              <li>1 → returns the part before '>'</li>
     *              <li>2 → returns the part after '>'</li>
     *              </ul>
     *
     * @return the requested part of the string, or {@code null} if the delimiter
     *         is missing or the event value is invalid
     *
     * @example
     *          slice("Message>ClientNumber", 1) → "Message"<br>
     *          slice("Message>ClientNumber", 2) → "ClientNumber"
     */

    public String sliceString(String str, int event) {
        if (str == null || !str.contains(">"))
            return null;

        String[] parts = str.split(">");

        if (event == 1) {
            return parts[0];
        } else if (event == 2) {
            return parts.length > 1 ? parts[1] : null;
        } else {
            return null;
        }
    }

    public int getClientNumber(String req) {
        // Message>ClientNumber
        // 22>Hello
        // e>Hello
        // >Hello
        // +1>Hello
        // 1>Hello
        if (req.isEmpty())
            return -1;
        if (!Character.isDigit(req.charAt(0))) {
            return -1; // send message to anyOne
        }
        if ((req.contains("-"))) {
            int index = req.indexOf("-") - 1;
            req.split(req, index);
            return 0;
        } else {
            return -1;
        }
    }

}
