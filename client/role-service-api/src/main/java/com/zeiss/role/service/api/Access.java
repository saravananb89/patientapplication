package com.zeiss.role.service.api;

public enum Access {
    NO_ACCESS(0),
    READ_ONLY_ACCESS(1),
    READ_WRITE_ACCESS(2);

    private int representation;

    private Access(int representation) {
        this.representation = representation;
    }

    public int getRepresentation() {
        return representation;
    }

    public static Access getAccess(int representation) {
        for (Access access : Access.values()) {
            if (access.getRepresentation() == representation) {
                return access;
            }
        }
        return NO_ACCESS;
    }
}
