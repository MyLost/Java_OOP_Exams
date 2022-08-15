package glacialExpedition;

import glacialExpedition.models.explorers.Explorer;
import glacialExpedition.models.explorers.GlacierExplorer;
import glacialExpedition.models.explorers.NaturalExplorer;
import glacialExpedition.models.mission.Mission;
import glacialExpedition.models.mission.MissionImpl;
import glacialExpedition.models.states.State;
import glacialExpedition.models.states.StateImpl;

import java.util.ArrayList;
import java.util.Collection;

public class Test {
    public static void main(String[] args) {
        State state = new StateImpl("South");
        state.getExhibits().add("pero");
        state.getExhibits().add("kamak");
        state.getExhibits().add("strela");

        Explorer explorerNat = new NaturalExplorer("Sam");
        Explorer explorerGlac = new GlacierExplorer("Tom");

        Collection<Explorer> explorerCollection = new ArrayList<>();
        explorerCollection.add(explorerNat);
        explorerCollection.add(explorerGlac);

        Mission mission = new MissionImpl();
        mission.explore(state, explorerCollection);
    }
}
