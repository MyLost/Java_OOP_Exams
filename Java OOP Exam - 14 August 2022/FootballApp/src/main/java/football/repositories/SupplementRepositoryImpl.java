package football.repositories;

import football.entities.supplement.Supplement;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class SupplementRepositoryImpl implements SupplementRepository {

    private Map<String, Supplement> supplements;

    public SupplementRepositoryImpl() {
        this.supplements = new LinkedHashMap<>();
    }

    @Override
    public void add(Supplement supplement) {
        supplements.put(supplement.getClass().getSimpleName(), supplement);
    }

    @Override
    public boolean remove(Supplement supplement) {
        return supplements.remove(supplement) != null;
    }

    @Override
    public Supplement findByType(String type) {
        return supplements.get(type);
    }
}
