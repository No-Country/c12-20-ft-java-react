package c1220ftjavareact.gym.user.email;

/**
 * Interfaz que para aplicar el patron Strategy a los template
 */
public interface TemplateStrategy {

    String buildTemplate();

    String replaceParameters(Object... values);
}
