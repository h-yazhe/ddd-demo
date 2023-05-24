package com.hyz.ddd.demo.application.dto;

import com.hyz.ddd.demo.domain.model.Mentor;
import lombok.Data;

/**
 * name：
 * desc:
 *
 * @author HuangYaZhe
 * @since 2023/5/22
 */
@Data
public class MentorDTO {

    private Long id;

    private String name;

    public static MentorDTO from(Mentor mentor) {
        MentorDTO mentorDTO = new MentorDTO();
        mentorDTO.setId(mentor.getId());
        mentorDTO.setName(mentor.getName());
        return mentorDTO;
    }
}
