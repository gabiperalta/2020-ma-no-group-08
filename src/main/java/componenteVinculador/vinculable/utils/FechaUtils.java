package componenteVinculador.vinculable.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class FechaUtils {

    public static Date obtenerFechaDiasAtras(Date fecha, int diasAtras) {
        LocalDate localDate = fecha.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        ZoneId defaultZoneId = ZoneId.systemDefault();

        localDate = localDate.minusDays(diasAtras);

        return  Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
    }

    public static Date obtenerFechaDiasAdelante(Date fecha, int diasAtras) {
        ZoneId defaultZoneId = ZoneId.systemDefault();

        LocalDate localDate = LocalDate.now().plusDays(diasAtras);

        return  Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
    }

    public static boolean estaDentroDelRango(Date fechaInicial, Date fechaBuscada, int rangoDias) {
        return FechaUtils.obtenerFechaDiasAtras(fechaInicial, rangoDias).before(fechaBuscada) ||
                FechaUtils.obtenerFechaDiasAdelante(fechaInicial, rangoDias).after(fechaBuscada);


    }
}
