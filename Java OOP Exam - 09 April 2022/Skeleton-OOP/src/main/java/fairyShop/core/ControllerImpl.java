package fairyShop.core;

import fairyShop.models.*;
import fairyShop.repositories.HelperRepository;
import fairyShop.repositories.PresentRepository;

import java.util.List;
import java.util.stream.Collectors;

import static fairyShop.common.ConstantMessages.*;
import static fairyShop.common.ExceptionMessages.*;

public class ControllerImpl implements Controller {

    private HelperRepository helperRepository;
    private PresentRepository presentRepository;

    public ControllerImpl() {
        this.helperRepository = new HelperRepository();
        this.presentRepository = new PresentRepository();
    }

    @Override
    public String addHelper(String type, String helperName) {

        Helper helper;
        if(type.equals(Happy.class.getSimpleName())) {
            helper = new Happy(helperName);
        } else if (type.equals(Sleepy.class.getSimpleName())) {
            helper = new Sleepy(helperName);
        } else {
            throw new IllegalArgumentException(HELPER_TYPE_DOESNT_EXIST);
        }

        helperRepository.add(helper);

        return String.format(ADDED_HELPER, type, helperName);
    }

    @Override
    public String addInstrumentToHelper(String helperName, int power) {

        Helper helper = helperRepository.findByName(helperName);

        if(helper == null) {
            throw new IllegalArgumentException(HELPER_DOESNT_EXIST);
        }

        Instrument instrument = new InstrumentImpl(power);
        helper.addInstrument(instrument);

        return String.format(SUCCESSFULLY_ADDED_INSTRUMENT_TO_HELPER, power, helperName);
    }

    @Override
    public String addPresent(String presentName, int energyRequired) {
        Present present = new PresentImpl(presentName, energyRequired);
        presentRepository.add(present);

        return String.format(SUCCESSFULLY_ADDED_PRESENT, presentName);
    }

    @Override
    public String craftPresent(String presentName) {

        Helper validHelper = helperRepository.getModels()
                .stream()
                .filter(h -> h.getEnergy() > 50)
                .findFirst().orElse(null);

        if(validHelper == null) {
            throw new IllegalArgumentException(NO_HELPER_READY);
        }

        Shop shop = new ShopImpl();
        Present present = presentRepository.findByName(presentName);

        shop.craft(present, validHelper);

        var brokenInstrument = validHelper.getInstruments().stream().filter(Instrument::isBroken).count();

        return String.format(PRESENT_DONE + COUNT_BROKEN_INSTRUMENTS, presentName, present.isDone() ? "done" : "not done", brokenInstrument);
    }

    @Override
    public String report() {

        StringBuilder stringBuilder = new StringBuilder();

        var countCraftedPresents = presentRepository.getModels().stream().filter(present -> present.isDone()).count();

        stringBuilder.append(String.format("%d presents are done!", countCraftedPresents));
        stringBuilder.append(System.lineSeparator());
        stringBuilder.append("Helpers info:");
        stringBuilder.append(System.lineSeparator());

        for (Helper helper : helperRepository.getModels()) {

            stringBuilder.append(String.format("Name: %s", helper.getName()));
            stringBuilder.append(System.lineSeparator());

            stringBuilder.append(String.format("Energy: %d", helper.getEnergy()));
            stringBuilder.append(System.lineSeparator());

            stringBuilder.append(String.format("Instruments: %d not broken left", helper.getInstruments().stream().filter(instrument -> !instrument.isBroken()).count()));
            stringBuilder.append(System.lineSeparator());
        }

        return stringBuilder.toString().trim();
    }
}
