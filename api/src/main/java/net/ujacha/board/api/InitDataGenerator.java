package net.ujacha.board.api;

import lombok.RequiredArgsConstructor;
import net.ujacha.board.api.entity.Member;
import net.ujacha.board.api.entity.MemberRole;
import net.ujacha.board.api.repository.MemberRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InitDataGenerator implements ApplicationRunner {
    private final MemberRepository memberRepository;
    @Override
    public void run(ApplicationArguments args) throws Exception {

        // admin user
        generateAdmin();

        // base data
        generateBaseData();

        // sample data
        generateSampleData();

    }


    private void generateAdmin() {
        memberRepository.save(new Member("admin@ujacha.net","admin1234","ADMIN", MemberRole.ADMIN));
    }

    private void generateBaseData() {
        //TODO
    }

    private void generateSampleData() {
        //TODO
    }
}
