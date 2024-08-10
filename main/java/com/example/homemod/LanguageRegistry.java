package com.example.homemod;

import java.util.HashMap;
import java.util.Map;

public class LanguageRegistry {
    private static final Map<String, Map<String, String>> translations = new HashMap<>();

    static {
        // Langue par défaut (français)
        Map<String, String> fr_fr = new HashMap<>();
        fr_fr.put("home_set", "Maison %s définie avec succès !");
        fr_fr.put("home_exists", "Une maison avec ce nom existe déjà.");
        fr_fr.put("home_limit", "Vous avez atteint le nombre maximum de maisons (5).");
        fr_fr.put("home_not_found", "Aucune maison trouvée avec le nom %s.");
        fr_fr.put("home_deleted", "Maison %s supprimée avec succès !");
        fr_fr.put("teleport_success", "Téléporté à la maison %s.");
        translations.put("fr_fr", fr_fr);

        // Langue anglaise
        Map<String, String> en_us = new HashMap<>();
        en_us.put("home_set", "Home %s set successfully!");
        en_us.put("home_exists", "A home with this name already exists.");
        en_us.put("home_limit", "You have reached the maximum number of homes (5).");
        en_us.put("home_not_found", "No home found with the name %s.");
        en_us.put("home_deleted", "Home %s deleted successfully!");
        en_us.put("teleport_success", "Teleported to home %s.");
        translations.put("en_us", en_us);

        // Langue espagnole
        Map<String, String> es_es = new HashMap<>();
        es_es.put("home_set", "Casa %s establecida con éxito!");
        es_es.put("home_exists", "Ya existe una casa con este nombre.");
        es_es.put("home_limit", "Has alcanzado el número máximo de casas (5).");
        es_es.put("home_not_found", "No se encontró ninguna casa con el nombre %s.");
        es_es.put("home_deleted", "Casa %s eliminada con éxito!");
        es_es.put("teleport_success", "Teletransportado a la casa %s.");
        translations.put("es_es", es_es);
    }

    public static String getTranslation(String key, String language) {
        return translations.getOrDefault(language, translations.get("fr_fr")).get(key);
    }

    public static boolean isLanguageSupported(String language) {
        return translations.containsKey(language);
    }
}
