package football.core;

import football.entities.field.ArtificialTurf;
import football.entities.field.Field;
import football.entities.field.NaturalGrass;
import football.entities.player.Men;
import football.entities.player.Player;
import football.entities.player.Women;
import football.entities.supplement.Liquid;
import football.entities.supplement.Powdered;
import football.entities.supplement.Supplement;
import football.repositories.SupplementRepository;
import football.repositories.SupplementRepositoryImpl;

import java.util.ArrayList;
import java.util.Collection;

import static football.common.ConstantMessages.*;
import static football.common.ExceptionMessages.*;

public class ControllerImpl implements Controller {

    private SupplementRepository supplementRepository;
    private Collection<Field> fields;

    public ControllerImpl() {
        this.supplementRepository = new SupplementRepositoryImpl();
        this.fields = new ArrayList<>();
    }

    @Override
    public String addField(String fieldType, String fieldName) {

        Field field = null;
        if(fieldType.equals("ArtificialTurf")) {
            field = new ArtificialTurf(fieldName);
        } else if (fieldType.equals("NaturalGrass")) {
            field = new NaturalGrass(fieldName);
        } else {
            throw new NullPointerException(INVALID_FIELD_TYPE);
        }

        fields.add(field);

        return String.format(SUCCESSFULLY_ADDED_FIELD_TYPE, fieldType);
    }

    @Override
    public String deliverySupplement(String type) {

        Supplement supplement = null;
        if(type.equals("Powdered")) {
            supplement = new Powdered();
        } else if (type.equals("Liquid")) {
            supplement = new Liquid();
        } else {
            throw new NullPointerException(INVALID_SUPPLEMENT_TYPE);
        }

        supplementRepository.add(supplement);
        return String.format(SUCCESSFULLY_ADDED_SUPPLEMENT_TYPE, type);
    }

    @Override
    public String supplementForField(String fieldName, String supplementType) {

        Supplement supplement = null;
        if(supplementType.equals("Powdered")) {
            supplement = supplementRepository.findByType(supplementType);
        } else if (supplementType.equals("Liquid")) {
            supplement = supplementRepository.findByType(supplementType);
        }
        else {
            throw new NullPointerException(String.format(NO_SUPPLEMENT_FOUND, supplementType));
        }

        Field field = fields.stream().filter(f -> f.getName().equals(fieldName)).findFirst().orElse(null);
        field.addSupplement(supplement);
        supplementRepository.remove(supplement);
        return String.format(SUCCESSFULLY_ADDED_SUPPLEMENT_IN_FIELD, supplementType, fieldName);
    }

    @Override
    public String addPlayer(String fieldName, String playerType, String playerName, String nationality, int strength) {

        Player player= null;

        if(playerType.equals("Women")) {
            player = new Women(playerName, nationality, strength);
        } else if (playerType.equals("Men")) {
            player = new Men(playerName, nationality, strength);
        } else {
            throw new IllegalArgumentException(INVALID_PLAYER_TYPE);
        }

        Field field = fields.stream().filter(f -> f.getName().equals(fieldName)).findFirst().orElse(null);

        if(field.getClass().getSimpleName().equals("ArtificialTurf") && playerType.equals("Women")) {
            return String.format(SUCCESSFULLY_ADDED_PLAYER_IN_FIELD, playerType, fieldName);
        } else if (field.getClass().getSimpleName().equals("NaturalGrass") && playerType.equals("Men")) {
            return String.format(SUCCESSFULLY_ADDED_PLAYER_IN_FIELD, playerType, fieldName);
        }

        field.addPlayer(player);
        return String.format(FIELD_NOT_SUITABLE);
    }

    @Override
    public String dragPlayer(String fieldName) {
        Field field = fields.stream().filter(f -> f.getName().equals(fieldName)).findFirst().orElse(null);
        field.drag();
        return String.format(PLAYER_DRAG, field.getPlayers().size());
    }

    @Override
    public String calculateStrength(String fieldName) {

        Field field = fields.stream().filter(f -> f.getName().equals(fieldName)).findFirst().orElse(null);

        var strength = field.getPlayers().stream().mapToInt(player -> player.getStrength()).sum();

        return String.format(STRENGTH_FIELD, fieldName, strength);
    }

    @Override
    public String getStatistics() {

        StringBuilder stringBuilder = new StringBuilder();
        for (Field field : fields) {
            stringBuilder.append(field.getInfo());
        }

        return stringBuilder.toString().trim();
    }

    @Override
    public String toString() {
        return getStatistics();
    }
}
