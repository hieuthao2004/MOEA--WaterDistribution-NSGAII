### Bài toán tối ưu hóa phân phối nước

#### Ý tưởng chính:
   -  Tối ưu hóa cách mà nước được phân phối đến các khu vực khác nhau trong một hệ thống cấp nước, nhằm đạt được hiệu quả cao nhất và giảm thiểu lãng phí. 
   - Đây là một dạng **bài toán tối ưu hóa đa mục tiêu** vì nó liên quan đến nhiều yếu tố cần được tối ưu đồng thời.

#### Mục tiêu của bài toán:
   - Bài toán có hai mục tiêu chính:
   1. **Tối đa hóa hiệu quả phân phối nước**:
      - Mục tiêu này liên quan đến việc cung cấp đủ lượng nước cho các khu vực mà không gây ra thiếu nước. Hiệu quả có thể được đo bằng cách so sánh lượng nước cung cấp so với nhu cầu của các khu vực.
   
   2. **Tối thiểu hóa lãng phí nước và chi phí**:
      - Mục tiêu này nhằm giảm thiểu lượng nước bị lãng phí (nước dư thừa không cần thiết) và chi phí vận hành hệ thống phân phối, chẳng hạn như năng lượng cần thiết để vận chuyển nước hoặc chi phí xử lý nước không sử dụng.

### Biến quyết định:
   - Biến quyết định trong bài toán có thể bao gồm:
      + **Lượng nước** được phân phối đến từng khu vực.
      + **Cấu trúc hệ thống phân phối** (ví dụ: chọn tuyến đường dẫn nước, kích thước đường ống...).
      + **Nguồn cấp nước**: Chọn nguồn nước phù hợp từ các hồ chứa, sông ngòi, hoặc các nguồn cung cấp nước khác.

### Ràng buộc:
- Tổng lượng nước phân phối không được vượt quá tổng lượng nước sẵn có 5000 L.


### Cách tiếp cận giải bài toán:
   - Bài toán này được giải bằng cách sử dụng thuật toán **NSGA-II** (Non-dominated Sorting Genetic Algorithm II) từ thư viện MOEA Framework. 
   - NSGA-II là một thuật toán tiến hóa đa mục tiêu giúp tối ưu hóa đồng thời nhiều mục tiêu.

#### Quy trình giải:
   1. **Tạo một tập hợp giải pháp ban đầu** (population) với các giá trị ngẫu nhiên cho biến quyết định.
   2. **Đánh giá các giải pháp**: Mỗi giải pháp sẽ được đánh giá dựa trên hai hàm mục tiêu:
      - Hiệu quả phân phối nước.
      - Lãng phí nước và chi phí.
   3. **Lựa chọn giải pháp tốt nhất**: Các giải pháp tốt nhất sẽ được lưu lại dựa trên nguyên tắc Pareto, tức là những giải pháp mà không có giải pháp nào khác vượt trội hơn ở tất cả các mục tiêu.
   4. **Tạo thế hệ mới**: Các giải pháp mới sẽ được tạo ra dựa trên cơ chế tiến hóa (crossover và mutation) hoặc trong trường hợp của bạn, chúng có thể được thay thế bằng các phương pháp khác như local search.
   5. **Lặp lại**: Quá trình này được lặp lại cho đến khi tìm ra tập hợp giải pháp tối ưu.

### Đầu ra của bài toán:
   - Sau khi chạy thuật toán, bạn sẽ nhận được một tập hợp các giải pháp gọi là **tập hợp không bị chiếm ưu thế (Pareto front)**. Các giải pháp trong tập hợp này đều là những giải pháp tốt về mặt hiệu quả phân phối nước và lãng phí/chi phí. Bạn có thể lựa chọn giải pháp nào phù hợp nhất tùy theo nhu cầu thực tế.

### Ví dụ:
   - Nếu hệ thống cấp nước có 3 khu vực và lượng nước có sẵn là 1000 lít:

      + **Khu vực A** cần tối thiểu **200 lít**.
      + **Khu vực B** cần tối thiểu **300 lít**.
      + **Khu vực C** cần tối thiểu **400 lít**.

   - Hệ thống phải tìm ra cách phân phối lượng nước còn lại sao cho không lãng phí và chi phí thấp nhất, đồng thời đảm bảo rằng mỗi khu vực đều nhận đủ lượng nước yêu cầu.

---------------------------------------------------------------------------------------------------------------------------------------

**NSGA-II (Non-dominated Sorting Genetic Algorithm II)** là một thuật toán tối ưu đa mục tiêu phổ biến, thường được sử dụng để giải các bài toán tối ưu hóa với nhiều mục tiêu mâu thuẫn nhau. Nó thuộc nhóm thuật toán tiến hóa và hoạt động dựa trên nguyên tắc chọn lọc tự nhiên của Darwin. Dưới đây là cách hoạt động của NSGA-II:

### Bước 1: Khởi tạo quần thể (Population Initialization)
- Một quần thể các cá thể (giải pháp tiềm năng) được khởi tạo ngẫu nhiên. Mỗi cá thể đại diện cho một giải pháp ứng cử viên trong không gian tìm kiếm, và có các giá trị của các biến quyết định.
- Mỗi cá thể sẽ được đánh giá theo các hàm mục tiêu khác nhau.

### Bước 2: Phân loại không trội (Non-dominated Sorting)
- NSGA-II sử dụng khái niệm **dominance** để sắp xếp các cá thể:
  - Một cá thể A được gọi là trội hơn (dominates) cá thể B nếu A tốt hơn hoặc bằng B trong tất cả các mục tiêu và tốt hơn B ít nhất một mục tiêu.
- Quần thể được phân loại thành nhiều **fronts** dựa trên mức độ trội. **Front 1** chứa các cá thể không bị trội bởi bất kỳ cá thể nào khác (non-dominated solutions). **Front 2** chứa các cá thể bị trội chỉ bởi các cá thể trong Front 1, và cứ thế tiếp tục.

### Bước 3: Tính toán khoảng cách Crowding Distance
- **Crowding Distance** là một thước đo để đánh giá mức độ "đông đúc" của các cá thể trong không gian mục tiêu.
- NSGA-II sử dụng giá trị này để duy trì đa dạng hóa trong quần thể, giúp tránh bị "tụ lại" vào một vùng cụ thể của không gian tìm kiếm.
- Trong mỗi front, cá thể có **crowding distance** cao hơn sẽ được ưu tiên hơn khi chọn lọc.

### Bước 4: Lựa chọn theo chiến lược Tournament Selection
- Thuật toán sử dụng **Binary Tournament Selection**, trong đó hai cá thể được chọn ngẫu nhiên, và cá thể nào có **front** tốt hơn sẽ được chọn.
- Nếu hai cá thể nằm trong cùng một front, cá thể nào có **crowding distance** lớn hơn sẽ được chọn.

### Bước 5: Phép lai (Crossover) và Đột biến (Mutation)
- Hai cá thể được chọn sẽ tiến hành **crossover** (lai ghép) để tạo ra thế hệ con.
- Một số cá thể sẽ trải qua **mutation** (đột biến), trong đó giá trị của các biến sẽ bị thay đổi ngẫu nhiên để đảm bảo đa dạng hóa trong quá trình tìm kiếm.

### Bước 6: Hợp nhất và cắt giảm quần thể (Combine and Reduce)
- Sau khi tạo ra quần thể con, NSGA-II kết hợp cả quần thể cha và con lại thành một quần thể tạm thời.
- Quần thể tạm thời này sau đó được phân loại và chỉ giữ lại một số cá thể tốt nhất dựa trên **rank** (mức độ trội) và **crowding distance**.

### Bước 7: Lặp lại quá trình
- Các bước từ 2 đến 6 được lặp lại cho đến khi đạt được số lượng thế hệ tối đa hoặc các tiêu chí hội tụ được thỏa mãn.

### Đặc điểm của NSGA-II:
1. **Non-dominated sorting** giúp thuật toán khám phá và tìm ra tập hợp các giải pháp Pareto tối ưu.
2. **Crowding Distance** đảm bảo tính đa dạng trong quần thể, tránh việc giải pháp tụ về một điểm cụ thể.
3. NSGA-II là một thuật toán mạnh mẽ để giải quyết các bài toán tối ưu hóa với nhiều mục tiêu đối lập mà không cần trọng số cụ thể cho từng mục tiêu.

### Ví dụ ứng dụng:
NSGA-II có thể được sử dụng trong các bài toán thực tiễn như:
- Thiết kế kỹ thuật (ví dụ: thiết kế máy bay hoặc ô tô để tối ưu hóa chi phí và hiệu suất).
- Phân bổ tài nguyên (ví dụ: phân phối nước, năng lượng).
- Các bài toán về logistic, lập lịch.

NSGA-II đặc biệt hữu ích khi cần tìm tập hợp các giải pháp tối ưu, thay vì chỉ một giải pháp duy nhất.