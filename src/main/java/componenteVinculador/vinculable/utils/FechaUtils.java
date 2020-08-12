package componenteVinculador.vinculable.utils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class FechaUtils {

    public Date obtenerFechaDiasAtras(int diasAtras) {
        Date dt = new Date();
        LocalDateTime localTime = LocalDateTime.from(dt.toInstant()).minusDays(diasAtras);

        return  Date.from(localTime.atZone(ZoneId.systemDefault()).toInstant());
    }
}
