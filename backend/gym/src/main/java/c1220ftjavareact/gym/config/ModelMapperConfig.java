package c1220ftjavareact.gym.config;

import c1220ftjavareact.gym.training.dto.TrainingSessionDTO;
import c1220ftjavareact.gym.training.dto.TrainingSessionSaveDTO;
import c1220ftjavareact.gym.training.entity.TrainingSession;
import c1220ftjavareact.gym.util.TimeFormatter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalTime;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        // Set the matching strategy to strict to avoid ambiguous property mappings
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapper.getConfiguration().setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE);

        // Configure the timeString formatter
        Converter<String, LocalTime> stringToLocalTimeConverter = context -> {
            String timeString = context.getSource();
            return timeString != null ? TimeFormatter.fromString(timeString) : null;
        };

        // Configure the custom mapping from TrainingSessionSaveDTO to TrainingSession
        modelMapper.typeMap(TrainingSessionSaveDTO.class, TrainingSession.class)
                .addMappings(mapper -> {
                    mapper.using(stringToLocalTimeConverter).map(TrainingSessionSaveDTO::getTimeStart, TrainingSession::setTimeStart);
                    mapper.using(stringToLocalTimeConverter).map(TrainingSessionSaveDTO::getTimeEnd, TrainingSession::setTimeEnd);
                });

        // Configure the custom mapping from TrainingSessionDTO to TrainingSession
        modelMapper.typeMap(TrainingSessionDTO.class, TrainingSession.class)
                .addMappings(mapper -> {
                    mapper.map(TrainingSessionDTO::getId, TrainingSession::setId);
                    mapper.using(stringToLocalTimeConverter).map(TrainingSessionDTO::getTimeStart, TrainingSession::setTimeStart);
                    mapper.using(stringToLocalTimeConverter).map(TrainingSessionDTO::getTimeEnd, TrainingSession::setTimeEnd);
                });

        // Configure the custom mapping from TrainingSession to TrainingSessionDTO
        modelMapper.typeMap(TrainingSession.class, TrainingSessionDTO.class)
                .addMappings(mapper -> {
                    mapper.map(src -> src.getActivity().getId(), TrainingSessionDTO::setActivityId);
                    mapper.map(src -> src.getRoom().getId(), TrainingSessionDTO::setRoomId);
                    mapper.map(src -> src.getRoom().getName(), TrainingSessionDTO::setRoomName);
                });

        return modelMapper;
    }
}