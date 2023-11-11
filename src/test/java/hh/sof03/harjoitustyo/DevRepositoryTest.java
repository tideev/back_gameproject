package hh.sof03.harjoitustyo;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import hh.sof03.harjoitustyo.domain.Developer;
import hh.sof03.harjoitustyo.domain.DeveloperRepository;

@DataJpaTest
public class DevRepositoryTest {

    @Autowired
    DeveloperRepository dRepository;

    @Test // testataan save()- ja findByName()-metodien toimivuutta
    public void testCreateAndSearchCategory() {
        Developer dev = new Developer("TestDev", "UK", 2000);
        dRepository.save(dev);
        assertThat(dev.getDevId()).isNotNull();

        List<Developer> developers = dRepository.findByName("TestDev");
        assertThat(developers).hasSize(1);
        assertThat(developers.get(0).getName()).isEqualTo("TestDev");
    }

    @Test // testataan delete()-metodin toimivuutta
    public void testDeleteDev() {
        Developer dev = new Developer("TestDev", "UK", 2000);
        dRepository.save(dev);

        Long devId = dev.getDevId();
        assertThat(dRepository.findById(devId)).isPresent();

        dRepository.deleteById(devId);
        assertThat(dRepository.findById(devId)).isEmpty();
    }
}
