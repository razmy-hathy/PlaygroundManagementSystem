##### Playground management system requirements

Create a library that implements playground management logic:
- The playground contains many different types of play sites, such as: double swings, carousel, slide, ball pit, etc.
- Kids of various ages can join the playground as long as they have a ticket. They can then choose to play at any play site.
- Strict regulations apply which forbid too many children playing at a single play site to ensure their safety, especially so in case of double swings which
  never allow for more than 2 kids.
- When a play site is full, kids can form a queue and wait until someone stops playing. However, impatient kids may choose to leave the queue early, or
  even disagree to wait to begin with.
- Tickets can be granted VIP status. VIP can skip to the front of the queue, but only a limited number of times.
- For the sake of fairness, the queue should maintain a balance of 1 VIP skip for every 3 non-VIPs. Given a queue of 5 non-VIPs, if 2 VIPs try to skip, one
  would expect the following outcome: NNNNN -> VNNNVNN
  Investors would like to know how well the playground is doing and are constantly asking:
- For any kid, provide history of play sites and how long did they play.
- For every play site, provide total visitor count per day and also break it down by visitor age.
- For every play site, provide utilization snapshots taken multiple times at intervals during the day.
  Utilization is measured in % of capacity used. It's usually proportional to the amount of kids playing, but not always. For example, double swings are not
  considered in use unless there are 2 kids playing.
  Technical notes:
- avoid irrelevant distractions such as DB, UI, etc. and focus on the logic
- provide evidence that your library works
- use gradle as your build management system if you can, it would make our lives easier
- upload the solution to a publicly accessible code repository such as github
- do your best!