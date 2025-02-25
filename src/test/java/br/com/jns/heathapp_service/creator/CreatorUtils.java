package br.com.jns.heathapp_service.creator;

import lombok.experimental.UtilityClass;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@UtilityClass
public class CreatorUtils {
    private static final PodamFactory factory = new PodamFactoryImpl();

    public static <T> T generateMock(final Class<T> tClass) {
        return factory.manufacturePojo(tClass);
    }
}
