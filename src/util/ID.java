package util;
import java.util.UUID;
public final class ID {
    private ID() {}
    public static String newId() { return UUID.randomUUID().toString(); }
}
