package glacialExpedition.repositories;

import glacialExpedition.models.states.State;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

public class StateRepository implements Repository<State>{

    private Map<String, State> state;


    @Override
    public Collection<State> getCollection() {
        return Collections.unmodifiableCollection(state.values());
    }

    @Override
    public void add(State entity) {
        state.put(entity.getName(), entity);
    }

    @Override
    public boolean remove(State entity) {
        return state.remove(entity.getName()) != null;
    }

    @Override
    public State byName(String name) {
        return state.get(name);
    }
}
