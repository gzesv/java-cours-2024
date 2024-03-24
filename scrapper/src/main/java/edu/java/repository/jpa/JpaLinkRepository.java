package edu.java.repository.jpa;

import edu.java.model.Link;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaLinkRepository extends JpaRepository<Link, Long> { }
