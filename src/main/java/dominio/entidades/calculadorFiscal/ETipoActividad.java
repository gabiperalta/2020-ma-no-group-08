package dominio.entidades.calculadorFiscal;

public enum ETipoActividad {

    CONSTRUCCION {
        @Override
        public String toString() {
            return "construccion";
        }
    },
    SERVICIOS {
        @Override
        public String toString() {
            return "servicios";
        }
    },
    COMERCIO {
        @Override
        public String toString() {
            return "comercio";
        }
    },
    INDMIN {
        @Override
        public String toString() {
            return "industriaMineria";
        }
    },
    AGROPECUARIO {
        @Override
        public String toString() {
            return "agropecuario";
        }
    }

}
