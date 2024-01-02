package pl.edu.prz.baw.houston.fed.auth

import java.security.MessageDigest

class AuthUtils {
    static String getSHA256(String base) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256")
            byte[] hash = digest.digest(base.getBytes("UTF-8"))
            StringBuilder hexString = new StringBuilder()

            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b)
                if (hex.length() == 1) hexString.append('0')
                hexString.append(hex)
            }

            return hexString.toString()
        } catch (Exception ex) {
            throw new RuntimeException(ex)
        }
    }
}
