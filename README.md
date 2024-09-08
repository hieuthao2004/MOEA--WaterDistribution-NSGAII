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

### Các ràng buộc:
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