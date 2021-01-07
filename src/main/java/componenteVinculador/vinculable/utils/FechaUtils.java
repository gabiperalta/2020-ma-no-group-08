package componenteVinculador.vinculable.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class FechaUtils {

    public static Date obtenerFechaDiasAtras(Date fecha, int diasAtras) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        String test = sdf.format(fecha);

        Date newDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").parse(test);
        LocalDate localDate = newDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        ZoneId defaultZoneId = ZoneId.systemDefault();

        localDate = localDate.minusDays(diasAtras);

        return  Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
    }

    public static Date obtenerFechaDiasAdelante(Date fecha, int diasAtras) {
        ZoneId defaultZoneId = ZoneId.systemDefault();

        LocalDate localDate = LocalDate.now().plusDays(diasAtras);

        return  Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
    }

    public static boolean estaDentroDelRango(Date fechaInicial, Date fechaBuscada, int rangoDias) throws ParseException {
        return FechaUtils.obtenerFechaDiasAtras(fechaInicial, rangoDias).before(fechaBuscada) ||
                FechaUtils.obtenerFechaDiasAdelante(fechaInicial, rangoDias).after(fechaBuscada);


    }
}
