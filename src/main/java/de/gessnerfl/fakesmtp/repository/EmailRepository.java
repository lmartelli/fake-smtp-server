package de.gessnerfl.fakesmtp.repository;

import de.gessnerfl.fakesmtp.model.Email;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
public class EmailRepository extends Repository<Email> {

    public int deleteEmailsExceedingDateRetentionLimit(int maxNumber) {
        synchronized (entities) {
            if (entities.size() > maxNumber) {
                int toDelete = entities.size() - maxNumber;
                var it = entities.keySet().iterator();
                int deleted = 0;
                while (deleted < toDelete) {
                    it.next();
                    it.remove();
                    deleted += 1;
                }
                return deleted;
            } else {
                return 0;
            }
        }
    }

    public List<Email> findBySubject(String subject) {
        return getEntities().stream()
                .filter(mail -> Objects.equals(mail.getSubject(), subject))
                .toList();
    }

    @Override
    protected int defaultOrder(Email o1, Email o2) {
        return o1.getId().compareTo(o2.getId());
    }
}
