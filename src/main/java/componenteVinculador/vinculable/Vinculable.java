package componenteVinculador.vinculable;

import componenteVinculador.vinculador.Vinculador;

import java.util.Date;

interface Vinculable {
    double getMonto();
    Date getFecha();
    boolean sePuedeVincularA(Vinculable vinculable);
}
