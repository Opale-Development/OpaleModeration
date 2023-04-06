package fr.opaleuhc.opalemoderation.enums;

public class Sanction {

    private final String reason;
    private final long duration;
    private final Sanction.Type type;

    public Sanction(String reason, long duration, Sanction.Type type) {
        this.reason = reason;
        this.duration = duration;
        this.type = type;
    }

    public String getReason() {
        return reason;
    }

    public long getDuration() {
        return duration;
    }

    public Sanction.Type getType() {
        return type;
    }

    public enum Type {
        BAN("bannir"),
        MUTE("rendre muet");

        private final String name;

        Type(String name) {
            this.name = name;
        }

        public static Type getByName(String name) {
            for (Type type : values()) {
                if (type.name().equalsIgnoreCase(name)) {
                    return type;
                }
            }
            return null;
        }

        public String getName() {
            return name;
        }
    }

}
