package com.hyz.ddd.demo.infrastructure.domain.impl.repository;

import com.hyz.ddd.demo.domain.model.Mentor;
import com.hyz.ddd.demo.domain.repository.MentorRepo;
import com.hyz.ddd.demo.infrastructure.DO.MentorDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

/**
 * name：通过rpc实现的导师仓库
 * desc:
 *
 * @author HuangYaZhe
 * @since 2023/5/22
 */
@Repository
public class RpcMentorRepoImpl implements MentorRepo {

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public List<Mentor> listAll() {
        RequestEntity<Object> request;
        try {
            request = RequestEntity
                    .post(new URI("http://localhost:8081/mentors"))
                    .accept(MediaType.APPLICATION_JSON)
                    .body(new Object());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        ResponseEntity<List<MentorDO>> response = restTemplate.exchange(request, new ParameterizedTypeReference<List<MentorDO>>() {
        });
        List<MentorDO> mentorDOS = response.getBody();
        if (mentorDOS == null) {
            throw new RuntimeException("mentorDOS is null");
        }
        return mentorDOS.stream()
                .map(this::restore)
                .collect(Collectors.toList());
    }

    @Override
    public List<Mentor> listByIds(List<Long> mentorIds) {
        HashSet<Long> idSet = new HashSet<>(mentorIds);
        return listAll()
                .stream()
                .filter(mentor -> idSet.contains(mentor.getId()))
                .collect(Collectors.toList());
    }

    /**
     * 还原出领域对象
     * @param mentorDO
     * @return
     */
    private Mentor restore(MentorDO mentorDO) {
        if (mentorDO == null) {
            return null;
        }
        return new Mentor(mentorDO.getId(), mentorDO.getName());
    }

}
