package componenteVinculador.vinculable.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class FechaUtils {

    public static Date obtenerFechaDiasAtras(int diasAtras) {
        ZoneId defaultZoneId = ZoneId.systemDefault();

        LocalDate localDate = LocalDate.now().minusDays(diasAtras);

        return  Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
    }
}
