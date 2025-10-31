package hansanhha;

import org.springframework.batch.item.ItemProcessor;

public class PersonProcessor implements ItemProcessor<Person, ProcessedPerson>{

    @Override
    public ProcessedPerson process(Person person) throws Exception {
        return new ProcessedPerson(person.getFirstName() + " " + person.getLastName());
    }
    
}
