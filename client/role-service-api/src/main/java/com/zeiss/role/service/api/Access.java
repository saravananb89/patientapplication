package com.zeiss.role.service.api;

import java.util.EnumSet;

public enum Access {
    NO_ACCESS(0) {
        @Override
        public boolean hasWriteAccess() {
            return false;
        }

        @Override
        public boolean hasReadAccess() {
            return false;
        }
    },
    READ_ONLY_ACCESS(1) {
        @Override
        public boolean hasWriteAccess() {
            return false;
        }

        @Override
        public boolean hasReadAccess() {
            return true;
        }
    },
    READ_WRITE_ACCESS(2) {
        @Override
        public boolean hasWriteAccess() {
            return true;
        }

        @Override
        public boolean hasReadAccess() {
            return true;
        }
    };

    private int representation;

    Access(int representation) {
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

    public static Integer find(String val) {
        return EnumSet.allOf(Access.class)
                .stream()
                .filter(e -> e.toString().equals(val))
                .findFirst().get().getRepresentation();
    }

    public abstract boolean hasWriteAccess();

    public abstract boolean hasReadAccess();

}
