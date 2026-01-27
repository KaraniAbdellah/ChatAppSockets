package services;

public class Services {
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

}
